package org.study.pmqc.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Produto;
import org.study.pmqc.model.enums.TipoCombustivel;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {

    Optional<Produto> findFirstByDescricaoAndTipo(String descricao, TipoCombustivel tipo);
}
