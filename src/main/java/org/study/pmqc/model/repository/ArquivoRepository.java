package org.study.pmqc.model.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Arquivo;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ArquivoRepository extends CrudRepository<Arquivo, Integer> {

    Optional<Arquivo> findTopByDataReferenciaOrderByDataReferenciaAsc(final LocalDate dataReferencia);

}
