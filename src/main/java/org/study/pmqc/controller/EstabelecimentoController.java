package org.study.pmqc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.pmqc.controller.service.EstabelecimentoService;
import org.study.pmqc.model.dto.EstabelecimentoTO;
import org.study.pmqc.model.mapper.EstabelecimentoMapper;

import java.util.List;

@RestController
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService service;

    private EstabelecimentoMapper mapper;

    @GetMapping
    public @ResponseBody ResponseEntity<List<EstabelecimentoTO>> getEstabelecimentos(){

        return new ResponseEntity<>(mapper.toDTO(service.getEstabelecimentos()), HttpStatus.OK);
    }

}
