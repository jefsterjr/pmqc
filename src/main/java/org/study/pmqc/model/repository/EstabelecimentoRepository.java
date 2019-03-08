package org.study.pmqc.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.study.pmqc.model.entities.Estabelecimento;

import java.util.Optional;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Integer> {

    Optional<Estabelecimento> findFirstByCnpjPosto(String cnpjPosto);
}
