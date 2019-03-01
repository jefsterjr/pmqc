package org.study.pmqc.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Produto;

@Repository
public interface ProdutoRepository extends CrudRepository<Produto, Integer> {

}
