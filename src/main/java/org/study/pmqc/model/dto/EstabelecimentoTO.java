package org.study.pmqc.model.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
@Setter
public class EstabelecimentoTO {

    private Integer id;

    private String razaoSocialPosto;  //  Razão Social do Posto Revendedor de Combustível.

    private String cnpjPosto; // CNPJ do Posto Revendedor de Combustível.

    private String distribuidora;

    private EnderecoTO endereco;

    private List<AmostraTO> amostras;

}
