package org.study.pmqc.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_amostra")
    private String codigoAmostra;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "amostra_id")
    private List<Ensaio> ensaios;

    @Column(name = "data_amostra")
    private LocalDate data;

    @OneToOne
    private Produto produto;

    public void addEnsaios(Ensaio ensaio) {
        if (this.ensaios == null) this.ensaios = new ArrayList<>();
        this.ensaios.add(ensaio);
    }
}
