package org.study.pmqc.controller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
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
import org.study.pmqc.model.repository.EstabelecimentoRepository;
import org.study.pmqc.model.repository.ProdutoRepository;

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
import java.util.List;
import java.util.Optional;

@Component
public class ConsultaServiceImpl {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    private List<String> idsSalvos;


    private static final String TIME_ZONE = "America/Sao_Paulo";

    @Scheduled(cron="0 48 17 7 * ?", zone = TIME_ZONE)
    public List<ConsultaTO> getFile() {

        final List<String> urls = getUrls();
        List<ConsultaTO> consultas = new ArrayList<>();
        idsSalvos = new ArrayList<>();
        urls.forEach(url -> {
            try {
                final BufferedReader reader = gerReader(url);
                String linha;
                int qtdLinhas = 0;
                while ((linha = reader.readLine()) != null) {
                    ConsultaTO consulta;
                    if (qtdLinhas != 0) {
                        String[] objetos = linha.split(";");
                        if (!verificarIdsInserido(objetos[1])) {
                            consulta = new ConsultaTO();
                            consulta.setDataColeta(LocalDate.parse(objetos[0]));
                            consulta.setIdNumeric(objetos[1]);
                            consulta.setGrupoProduto(TipoCombustivel.fromString(objetos[2].toUpperCase()));
                            consulta.setProduto(objetos[3]);
                            consulta.setRazaoSocialPosto(objetos[4]);
                            consulta.setCnpjPosto(objetos[5]);
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
                    salvarEstabelecimento(consultas);
                    System.out.println(qtdLinhas);
                    if(qtdLinhas == 100){
                        break;

                    }
                }
                salvarArquivo( url.trim().substring(49));
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        return consultas;
    }

    @Transactional
    private void salvarEstabelecimento(final List<ConsultaTO> consultas) {

        consultas.forEach(consultaTO -> {
            Estabelecimento estabelecimento = new Estabelecimento();

            estabelecimento.setDistribuidora(consultaTO.getDistribuidora());
            estabelecimento.setRazaoSocialPosto(consultaTO.getRazaoSocialPosto());
            estabelecimento.setCnpjPosto(consultaTO.getCnpjPosto());

            estabelecimento.setEndereco(new Endereco(consultaTO.getLogradouro(), consultaTO.getComplemento(), consultaTO.getBairro(), consultaTO.getLatitude(), consultaTO.getLongitude()));

            Amostra amostra = new Amostra();
            amostra.setEstabelecimento(estabelecimento);
            amostra.setCodigoAmostra(consultaTO.getIdNumeric());
            amostra.setData(consultaTO.getDataColeta());

//            Optional<Produto> optProduto = produtoRepository.findFirstByDescricaoAndTipo(consultaTO.getProduto(), consultaTO.getGrupoProduto());
//            if(optProduto.isPresent()){
//                amostra.setProduto(optProduto.get());
//            }else{
                Produto produto = new Produto();
                produto.setDescricao(consultaTO.getProduto());
                produto.setTipo(consultaTO.getGrupoProduto());
                amostra.setProduto(produto);
//            }


            consultaTO.getEnsaios().forEach(ensaioTO -> {
                Ensaio ensaio = new Ensaio();
                ensaio.setAmostra(amostra);
                ensaio.setConfome(ensaioTO.getConfome());
                ensaio.setResultado(ensaioTO.getResultado());
                ensaio.setTipo(ensaioTO.getEnsaio());
                ensaio.setUnidadeEnsaio(ensaioTO.getUnidadeEnsaio());
                amostra.addEnsaios(ensaio);
            });

            estabelecimento.addAmostras(amostra);
            estabelecimentoRepository.save(estabelecimento);
        });
    }

    private void salvarArquivo(String nomeArquivo) {
        Arquivo arquivo = new Arquivo();
        arquivo.setNome(nomeArquivo);
        arquivo.setDataImportacao(LocalDate.now());
        Year anoArquivo = Year.of(Integer.valueOf(nomeArquivo.trim().substring(5, 9)));
        Month mesArquivo = Month.of(Integer.valueOf(nomeArquivo.trim().substring(10, 12)));
        arquivo.setDataReferencia(LocalDateTime.of(anoArquivo.getValue(), mesArquivo.getValue(), 1, 0, 0, 0).toLocalDate());

        arquivoRepository.save(arquivo);
    }

    private BufferedReader gerReader(String url) throws IOException {
        return consultarArquivos(url);
    }

    private EnsaioTO getEnsaio(final String[] objetos) {
        return new EnsaioTO(objetos[15].toUpperCase(), objetos[16], objetos[17], objetos[18]);
    }

    private Boolean verificarIdsInserido(final String idNumerico) {
        return idsSalvos.stream().anyMatch(id -> id.equals(idNumerico));
    }

    private BufferedReader consultarArquivos(final String urlString) throws IOException {
        final URL url = new URL(urlString);
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.239.68.1", 3128));
        URLConnection con = url.openConnection(proxy);
        return new BufferedReader(new InputStreamReader(con.getInputStream()));
    }

    private List<String> getUrls() {

//        final String urlBase = "http://www.anp.gov.br/images/dadosabertos/PMQC/";
        final String urlBase = "http://www.anp.gov.br/arquivos/dadosabertos/PMQC/";
        List<String> urls = new ArrayList<>();
        final Optional<Arquivo> arquivo = arquivoRepository.findTopByDataReferenciaOrderByDataReferenciaAsc(LocalDate.now().withDayOfMonth(1));
        getNomesArquivos(arquivo).forEach(nomeArquivo -> {
            urls.add(urlBase + nomeArquivo);
        });
        return urls;
    }

    private List<String> getNomesArquivos(final Optional<Arquivo> arquivo) {
        int anoBase = 2016;

        List<String> nomes = new ArrayList<>();
        if (arquivo.isPresent()) {
            anoBase = arquivo.get().getDataReferencia().getYear();
            for (int ano = anoBase; ano < LocalDateTime.now().getYear(); ano++) {
                int mes = 1;
                for (mes = arquivo.get().getDataReferencia().getMonthValue(); mes <= 12; mes++) {
                    nomes.add("PMQC_" + ano + "_" + (Integer.toString(mes).length() == 2 ? mes : "0" + mes) + ".csv");
                }
            }
        } else {
            for (int ano = anoBase; ano < LocalDateTime.now().getYear(); ano++) {
                for (int mes = 1; mes <= 12; mes++) {
                    nomes.add("PMQC_" + ano + "_" + (Integer.toString(mes).length() == 2 ? mes : "0" + mes) + ".csv");
                }
            }
        }
        List<String> nommesTeste= new ArrayList<>();
        nommesTeste.add("PMQC_2019_01.csv");
        return nommesTeste;
    }
}
