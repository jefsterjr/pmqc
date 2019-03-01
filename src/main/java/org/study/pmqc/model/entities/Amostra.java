package org.study.pmqc.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "amostra")
public class Amostra {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "codigo_amostra")
    private String codigoAmostra;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "amostra")
    private List<Ensaio> ensaios;

    @Column(name = "data_amostra")
    private LocalDate data;

    @OneToOne(mappedBy = "amostra", cascade = CascadeType.ALL)
    private Produto produto;

    @JsonIgnore
    @ManyToOne
    private Estabelecimento estabelecimento;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCodigoAmostra() {
        return codigoAmostra;
    }

    public void setCodigoAmostra(String codigoAmostra) {
        this.codigoAmostra = codigoAmostra;
    }

    public List<Ensaio> getEnsaios() {
        return ensaios;
    }

    public void setEnsaios(List<Ensaio> ensaios) {
        this.ensaios = ensaios;
    }


    public void addEnsaios(Ensaio ensaio) {
        if (this.ensaios == null) this.ensaios = new ArrayList<>();
        this.ensaios.add(ensaio);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

}
