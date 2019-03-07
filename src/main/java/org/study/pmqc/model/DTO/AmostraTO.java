package org.study.pmqc.model.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class AmostraTO {

    private Integer id;

    private String codigoAmostra;

    private List<EnsaioTO> ensaios;

    private LocalDate data;

    private ProdutoTO produto;

    @JsonIgnore
    private EstabelecimentoTO estabelecimento;
}
