package org.study.pmqc.util.task;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.study.pmqc.controller.service.ConsultaArquivoService;
import org.study.pmqc.model.dto.ConsultaTO;

import java.net.ConnectException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class ConsultaTask {

    @Autowired
    private ConsultaArquivoService service;

    private static final String TIME_ZONE = "America/Sao_Paulo";

    private static final Logger logger = LogManager.getLogger(ConsultaTask.class);

    @Scheduled(cron = "0 56 11 8 * ?", zone = TIME_ZONE)
    public void getFile() {
        logger.info("Iniciando consulta de Arquivos PMQC " + LocalDateTime.now());
        service.getUrls().forEach(url -> {
            try {
                final List<ConsultaTO> consultas = service.consultarArquivos(url);
                consultas.forEach(consultaTO -> service.salvarEstabelecimento(consultaTO));
                service.salvarArquivo(url.trim().substring(49));
            } catch (ConnectException c) {
                logger.warn("Erro na na conexão", c);
                getFile();
            } catch (Exception e) {
                logger.error(e);
            }

        });
    }
}
