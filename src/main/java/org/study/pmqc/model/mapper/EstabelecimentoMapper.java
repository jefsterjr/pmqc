package org.study.pmqc.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.study.pmqc.model.dto.EstabelecimentoTO;
import org.study.pmqc.model.entities.Estabelecimento;

@Mapper(componentModel = "spring")
public interface EstabelecimentoMapper {

    EstabelecimentoMapper INSTANCE = Mappers.getMapper( EstabelecimentoMapper.class );

    EstabelecimentoTO toDTO(Estabelecimento estabelecimento);

    Estabelecimento toEntity(EstabelecimentoTO estabelecimentoTO);
}
