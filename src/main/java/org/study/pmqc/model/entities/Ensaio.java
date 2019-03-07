package org.study.pmqc.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.study.pmqc.model.enums.TipoEnsaio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "ensaio")
public class Ensaio {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoEnsaio tipo;

    @Column(name = "resultado")
    private String resultado;

    @Column(name = "unidade_ensaio")
    private String unidadeEnsaio;

    @Column(name = "confome")
    private Boolean confome;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "amostra_id")
    private Amostra amostra;

}
