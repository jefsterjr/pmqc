package org.study.pmqc.model.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Getter
@Setter

public class EnderecoTO {

    private Integer id;

    private String logradouro; // Endereço do posto de revenda de combustíveis

    private String complemento; // Complemento do endereço

    private String bairro;

    private String latitude;

    private String longitude;
}
