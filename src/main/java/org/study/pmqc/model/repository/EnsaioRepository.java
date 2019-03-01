package org.study.pmqc.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Ensaio;

@Repository
public interface EnsaioRepository extends CrudRepository<Ensaio, Integer> {

}
