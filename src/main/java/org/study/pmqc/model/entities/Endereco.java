package org.study.pmqc.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "logradouro")
    private String logradouro; // Endereço do posto de revenda de combustíveis

    @Column(name = "complemento")
    private String complemento; // Complemento do endereço

    @Column(name = "bairro")
    private String bairro;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    public Endereco(String logradouro, String complemento, String bairro, String latitude, String longitude) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;
        this.latitude = latitude;
        this.longitude = longitude;
    }

}
