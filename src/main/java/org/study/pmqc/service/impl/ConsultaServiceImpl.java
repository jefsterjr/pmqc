package org.study.pmqc.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pmqc.model.DTO.ConsultaTO;
import org.study.pmqc.model.DTO.EnsaioTO;
import org.study.pmqc.model.entities.*;
import org.study.pmqc.model.enums.TipoCombustivel;
import org.study.pmqc.model.repository.EstabelecimentoRepository;
import org.study.pmqc.service.ConsultaService;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private EstabelecimentoRepository estabelecimentoRepository;

    private List<String> idsSalvos;

    @Override
    public List<ConsultaTO> getFile() {
        List<ConsultaTO> consultas = new ArrayList<>();
        idsSalvos = new ArrayList<>();
        gerReaders().forEach(reader -> {
            try{
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
                }
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }


        });

        return consultas;
    }

    @Transactional
    private void salvarEstabelecimento(List<ConsultaTO> consultas) {
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

            Produto produto = new Produto();
            produto.setDescricao(consultaTO.getProduto());
            produto.setTipo(consultaTO.getGrupoProduto());
            amostra.setProduto(produto);

            consultaTO.getEnsaios().forEach(ensaioTO -> {
                Ensaio ensaio = new Ensaio();
                ensaio.setAmostra(amostra);
                ensaio.setConfome(ensaioTO.getConfome());
                ensaio.setResultado(ensaioTO.getResultado());
                ensaio.setTipo(ensaioTO.getEnsaio());
                amostra.addEnsaios(ensaio);
            });

            estabelecimento.addAmostras(amostra);
            estabelecimentoRepository.save(estabelecimento);
        });
    }

    private List<BufferedReader> gerReaders() {
        List<BufferedReader> listaReaders = new ArrayList<>();
        getUrls().forEach(url -> {
            try {
                listaReaders.add(consultarArquivos(url));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return listaReaders;
    }

    private EnsaioTO getEnsaio(String[] objetos) {
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
        List<String> urls = new ArrayList<>();
//        for (int ano = 2016; ano < LocalDateTime.now().getYear(); ano++) {
//            for (int mes = 1; mes < 12; mes++) {
//                urls.add("http://www.anp.gov.br/arquivos/dadosabertos/PMQC/PMQC_" + ano + "_" + (Integer.toString(mes).length() == 2 ? mes : "0" + mes) + ".csv");
                urls.add("http://www.anp.gov.br/arquivos/dadosabertos/PMQC/PMQC_2019_01.csv");
//            }
//        }
        return urls;
    }
}
