package org.study.pmqc.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.study.pmqc.service.ConsultaService;

@RestController
@RequestMapping("/consulta")
public class ConsultaController {

    @Autowired
    private ConsultaService service;


    @GetMapping("/consultar")
    public @ResponseBody
    ResponseEntity consultar(){
        service.getFile();
        return ResponseEntity.ok("OK");
    }

}
