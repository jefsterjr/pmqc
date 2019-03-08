package org.study.pmqc.controller.service;

import org.study.pmqc.model.dto.ConsultaTO;

import java.io.IOException;
import java.util.List;

public interface ConsultaArquivoService {

    List<String> getUrls();

    List<ConsultaTO> consultarArquivos(final String url) throws IOException;

    void salvarEstabelecimento(final ConsultaTO consultaTO);

    void salvarArquivo(final String nomeArquivo) ;
}
