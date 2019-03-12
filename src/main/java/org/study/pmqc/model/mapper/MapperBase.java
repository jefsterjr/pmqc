package org.study.pmqc.model.mapper;

import org.mapstruct.MappingTarget;
import org.study.pmqc.model.entities.EntidadeBase;

import java.util.List;

public interface MapperBase<E extends EntidadeBase, DTO> {

    DTO toDTO(E entity);

    E toEntity(DTO dto);

    List<DTO> toDTO(List<E> e);

    List<E> toEntity(List<DTO> dto);



    E map(DTO dto, @MappingTarget E entity);
}
