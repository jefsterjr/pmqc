package org.study.pmqc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.pmqc.controller.service.EstabelecimentoService;
import org.study.pmqc.model.entities.Estabelecimento;

import java.util.List;

@RestController
@RequestMapping("/estabelecimento")
public class EstabelecimentoController {

    @Autowired
    private EstabelecimentoService service;

    @GetMapping
    public @ResponseBody ResponseEntity<List<Estabelecimento>> getEstabelecimentos(){
        return new ResponseEntity<>(service.getEstabelecimentos(), HttpStatus.OK);
    }

}
