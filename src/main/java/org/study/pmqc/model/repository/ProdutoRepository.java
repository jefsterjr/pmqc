package org.study.pmqc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Produto;
import org.study.pmqc.model.enums.TipoCombustivel;

import java.util.Optional;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    Optional<Produto> findFirstByDescricaoAndTipo(String descricao, TipoCombustivel tipo);
}
