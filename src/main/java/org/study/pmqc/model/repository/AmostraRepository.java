package org.study.pmqc.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Amostra;

@Repository
public interface AmostraRepository extends CrudRepository<Amostra, Integer> {

}
