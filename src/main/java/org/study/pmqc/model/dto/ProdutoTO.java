package org.study.pmqc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.study.pmqc.model.enums.TipoCombustivel;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class ProdutoTO {

    private Integer id;

    private String descricao;

    private TipoCombustivel tipo;

    @JsonIgnore
    private AmostraTO amostra;
}
