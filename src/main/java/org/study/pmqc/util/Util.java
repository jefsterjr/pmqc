package org.study.pmqc.util;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.study.pmqc.model.Consulta;
import org.study.pmqc.model.DTO.EmpresaTO;
import org.study.pmqc.model.DTO.EnderecoTO;
import org.study.pmqc.model.DTO.EnsaioTO;
import org.study.pmqc.model.enums.TipoCombustivel;

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

public class Util {

    private List<String> idsSalvos;

    public String getFile() {
        try {
            final BufferedReader reader = consultarArquivos();
            int qtdLinhas = 0;
            String linha;
            idsSalvos = new ArrayList<>();
            while ((linha = reader.readLine()) != null) {

                if(qtdLinhas != 0){
                    String[] objetos = linha.split(",");
                    int i = 0;
                    Consulta c = new Consulta();
                    if(verificarIdsDuplicados(objetos[1])){
                        c.setDataColeta(LocalDate.parse(objetos[i++]));
                        c.setIdNumeric(objetos[i++]);
                        c.setGrupoProduto(TipoCombustivel.valueOf(objetos[i++].toUpperCase()));
                        c.setProduto(objetos[i++]);
                        c.setEmpresa(new EmpresaTO(objetos[i++], objetos[i++], objetos[i++]));
                        c.setLatitude(objetos[i++]);
                        c.setLongitude(objetos[i++]);
                        c.setEndereco(new EnderecoTO(objetos[i++], objetos[i++], objetos[i++]));
                    }else{
                        idsSalvos.add(objetos[1]);
                    }
                    c.addEnsaio(new EnsaioTO(objetos[i++],objetos[i++], objetos[i++], objetos[i]));
                }

                qtdLinhas++;
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "TESTE";
    }
    private Boolean verificarIdsDuplicados(final String idNumerico) {
        for (String id : idsSalvos) {
            if (id.equals(idNumerico)) {
                return false;
            }
        }
        return true;
    }

    private BufferedReader consultarArquivos() throws IOException {
        final String urlString = "http://www.anp.gov.br/arquivos/dadosabertos/PMQC/PMQC_2019_01.csv";
        final URL url = new URL(urlString);
        String linha;
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("10.239.68.1", 3128));
        URLConnection con = url.openConnection(proxy);
        return new BufferedReader(new InputStreamReader( con.getInputStream()));
    }
}
