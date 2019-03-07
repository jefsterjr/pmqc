package org.study.pmqc.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "amostra")
public class Amostra {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_amostra")
    private String codigoAmostra;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "amostra")
    private List<Ensaio> ensaios;

    @Column(name = "data_amostra")
    private LocalDate data;

    @OneToOne(cascade = CascadeType.ALL)
    private Produto produto;

    @JsonIgnore
    @ManyToOne
    private Estabelecimento estabelecimento;


    public void addEnsaios(Ensaio ensaio) {
        if (this.ensaios == null) this.ensaios = new ArrayList<>();
        this.ensaios.add(ensaio);
    }
}
