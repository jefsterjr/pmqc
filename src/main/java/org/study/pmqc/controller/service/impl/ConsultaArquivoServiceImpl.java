package org.study.pmqc.controller.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pmqc.controller.service.ConsultaArquivoService;
import org.study.pmqc.model.dto.ConsultaTO;
import org.study.pmqc.model.dto.EnsaioTO;
import org.study.pmqc.model.entities.Amostra;
import org.study.pmqc.model.entities.Arquivo;
import org.study.pmqc.model.entities.Endereco;
import org.study.pmqc.model.entities.Ensaio;
import org.study.pmqc.model.entities.Estabelecimento;
import org.study.pmqc.model.entities.Produto;
import org.study.pmqc.model.enums.TipoCombustivel;
import org.study.pmqc.model.repository.ArquivoRepository;
import org.study.pmqc.model.repository.EnsaioRepository;
import org.study.pmqc.model.repository.EstabelecimentoRepository;
import org.study.pmqc.model.repository.ProdutoRepository;
import org.study.pmqc.util.task.ConsultaTask;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class ConsultaArquivoServiceImpl implements ConsultaArquivoService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private EnsaioRepository ensaioRepository;

    private static final Logger logger = LogManager.getLogger(ConsultaTask.class);

    private List<String> idsSalvos;


    private EnsaioTO getEnsaio(final String[] objetos) {
        return new EnsaioTO(objetos[15].toUpperCase(), objetos[16], objetos[17], objetos[18]);
    }

    private Boolean verificarIdsInserido(final String idNumerico) {
        return idsSalvos.stream().anyMatch(id -> id.equals(idNumerico));
    }

    public List<String> getUrls() {

        AtomicReference<String> urlBase = new AtomicReference<>("http://www.anp.gov.br/images/dadosabertos/PMQC/");
        List<String> urls = new ArrayList<>();
        final Optional<Arquivo> arquivo = arquivoRepository.findTopByDataReferenciaOrderByDataReferenciaAsc(LocalDate.now().withDayOfMonth(1));

        getNomesArquivos(arquivo).forEach((nomeArquivo, data) -> {
            if (data.isAfter(LocalDateTime.of(2018, 11, 1, 1, 1))) {
                urlBase.set("http://www.anp.gov.br/arquivos/dadosabertos/PMQC/");
            }
            urls.add(urlBase + nomeArquivo);
        });
        return urls;
    }

    private Map<String, LocalDateTime> getNomesArquivos(final Optional<Arquivo> arquivo) {
        int anoBase = 2016;

        Map<String, LocalDateTime> nomesArquivos = new HashMap<>();

        if (arquivo.isPresent()) {
            anoBase = arquivo.get().getDataReferencia().getYear();
            for (int ano = anoBase; ano < LocalDateTime.now().getYear(); ano++) {
                int mes = 1;
                for (mes = arquivo.get().getDataReferencia().getMonthValue(); mes <= 12; mes++) {
                    nomesArquivos.put(("PMQC_" + ano + "_" + (Integer.toString(mes).length() == 2 ? mes : "0" + mes) + ".csv"), LocalDateTime.of(ano, mes, 1, 0, 1));
                }
            }
        } else {
            for (int ano = anoBase; ano < LocalDateTime.now().getYear(); ano++) {
                for (int mes = 1; mes <= 12; mes++) {
                    nomesArquivos.put(("PMQC_" + ano + "_" + (Integer.toString(mes).length() == 2 ? mes : "0" + mes) + ".csv"), LocalDateTime.of(ano, mes, 1, 0, 1));
                }
            }
        }
        return nomesArquivos;
    }

    @Transactional()
    public void salvarEstabelecimento(final ConsultaTO consultaTO) {
        Estabelecimento estabelecimento = getEstabelecimento(consultaTO);

        Amostra amostra = new Amostra();
        amostra.setCodigoAmostra(consultaTO.getIdNumeric());
        amostra.setData(consultaTO.getDataColeta());
        amostra.setProduto(saveProduto(consultaTO));

        consultaTO.getEnsaios().forEach(ensaioTO -> amostra.addEnsaios(saveEnsaio(ensaioTO)));

        estabelecimento.addAmostras(amostra);

        logger.debug("Salvando estabelecimento ", estabelecimento);
        estabelecimentoRepository.save(estabelecimento);
    }

    private Estabelecimento getEstabelecimento(final ConsultaTO consultaTO) {
        Optional<Estabelecimento> optionalEstabelecimento = estabelecimentoRepository.findFirstByCnpjPosto(consultaTO.getCnpjPosto());
        if (optionalEstabelecimento.isPresent()) {
            return optionalEstabelecimento.get();
        } else {
            Estabelecimento estabelecimento = new Estabelecimento();
            estabelecimento.setDistribuidora(consultaTO.getDistribuidora());
            estabelecimento.setRazaoSocialPosto(consultaTO.getRazaoSocialPosto());
            estabelecimento.setCnpjPosto(consultaTO.getCnpjPosto());
            estabelecimento.setEndereco(new Endereco(consultaTO.getLogradouro(), consultaTO.getComplemento(), consultaTO.getBairro(), consultaTO.getLatitude(), consultaTO.getLongitude()));
            return estabelecimento;
        }
    }

    private Ensaio saveEnsaio(final EnsaioTO ensaioTO) {
        Ensaio ensaio = new Ensaio();
        ensaio.setConfome(ensaioTO.getConfome());
        ensaio.setResultado(ensaioTO.getResultado());
        ensaio.setTipo(ensaioTO.getEnsaio());
        ensaio.setUnidadeEnsaio(ensaioTO.getUnidadeEnsaio());

        logger.debug("Salvando ensaio ", ensaioTO);

        return ensaioRepository.save(ensaio);
    }

    private Produto saveProduto(final ConsultaTO consultaTO) {

        Optional<Produto> optProduto = produtoRepository.findFirstByDescricaoAndTipo(consultaTO.getProduto(), consultaTO.getGrupoProduto());
        if (optProduto.isPresent()) {
            return optProduto.get();
        } else {
            Produto produto = new Produto();
            produto.setDescricao(consultaTO.getProduto());
            produto.setTipo(consultaTO.getGrupoProduto());

            logger.debug("Salvando produto ", produto);

            return produtoRepository.save(produto);
        }
    }

    public List<ConsultaTO> consultarArquivos(final String url) throws IOException {
        List<ConsultaTO> consultas = new ArrayList<>();
        final BufferedReader reader = consultarArquivo(url);
        String linha;
        int qtdLinhas = 0;
        while ((linha = reader.readLine()) != null) {
            ConsultaTO consulta;
            String[] objetos = linha.split(";");
            if (objetos[13].equals("GO")) {
                if (qtdLinhas != 0) {

                    if (!verificarIdsInserido(objetos[1])) {
                        consulta = new ConsultaTO();
                        consulta.setDataColeta(LocalDate.parse(objetos[0]));
                        consulta.setIdNumeric(objetos[1]);
                        consulta.setGrupoProduto(TipoCombustivel.fromString(objetos[2].toUpperCase()));
                        consulta.setProduto(objetos[3]);
                        consulta.setRazaoSocialPosto(objetos[4]);
                        consulta.setCnpjPosto(objetos[5].replace(".", "").replace("-", "").replace("/", ""));
                        consulta.setDistribuidora(objetos[6]);
                        consulta.setLogradouro(objetos[7]);
                        consulta.setComplemento(objetos[8]);
                        consulta.setBairro(objetos[9]);
                        consulta.setMunicipio(objetos[10]);
                        consulta.setLatitude(objetos[11]);
                        consulta.setLongitude(objetos[12]);
                        consulta.setUf(objetos[13]);
                        consulta.setRegiao(objetos[14]);
                        consulta.addEnsaio(getEnsaio(objetos));
                        idsSalvos.add(objetos[1]);
                        consultas.add(consulta);
                    } else {
                        if (consultas.size() > 0) {
                            for (ConsultaTO consultaTO : consultas) {
                                if (idsSalvos.stream().anyMatch(id -> id.equals(consultaTO.getIdNumeric()))) {
                                    consultaTO.addEnsaio(getEnsaio(objetos));
                                    break;
                                }
                            }
                        }
                    }
                }
                qtdLinhas++;
            }
        }
        reader.close();
        return consultas;
    }

    private BufferedReader consultarArquivo(final String urlString) throws IOException {
        logger.debug("Consultando arquivo ", urlString);
        final URL url = new URL(urlString);
        final Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.239.68.1", 3128));
        final URLConnection con = url.openConnection(proxy);
        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }

    public void salvarArquivo(final String nomeArquivo) {
        Arquivo arquivo = new Arquivo();
        arquivo.setNome(nomeArquivo);
        arquivo.setDataImportacao(LocalDate.now());
        Year anoArquivo = Year.of(Integer.valueOf(nomeArquivo.trim().substring(5, 9)));
        Month mesArquivo = Month.of(Integer.valueOf(nomeArquivo.trim().substring(10, 12)));
        arquivo.setDataReferencia(LocalDateTime.of(anoArquivo.getValue(), mesArquivo.getValue(), 1, 0, 0, 0).toLocalDate());
        logger.debug("Salvando arquivo ", arquivo);
        arquivoRepository.save(arquivo);
    }
}
