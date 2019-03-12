package org.study.pmqc.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.study.pmqc.model.dto.EstabelecimentoTO;
import org.study.pmqc.model.entities.Estabelecimento;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EstabelecimentoMapper extends MapperBase<Estabelecimento, EstabelecimentoTO> {

}
