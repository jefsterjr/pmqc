package org.study.pmqc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Amostra;

@Repository
public interface AmostraRepository extends JpaRepository<Amostra, Integer> {

}
