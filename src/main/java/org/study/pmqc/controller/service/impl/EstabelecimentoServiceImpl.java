package org.study.pmqc.controller.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.study.pmqc.controller.service.EstabelecimentoService;
import org.study.pmqc.model.entities.Estabelecimento;
import org.study.pmqc.model.repository.EstabelecimentoRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EstabelecimentoServiceImpl implements EstabelecimentoService {

    @Autowired
    private EstabelecimentoRepository repository;

    @Override
    public List<Estabelecimento> getEstabelecimentos() {
        List<Estabelecimento> estabeleciments = new ArrayList<>();
        repository.findAll().forEach(estabelecimento -> estabeleciments.add(estabelecimento));
        return estabeleciments;
    }
}
