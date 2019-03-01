package org.study.pmqc.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.study.pmqc.model.enums.TipoCombustivel;

import javax.persistence.*;

@Entity
@Table(name = "produto")
public class Produto {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "descricao")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo")
    private TipoCombustivel tipo;

    @JsonIgnore
    @OneToOne
    private Amostra amostra;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public TipoCombustivel getTipo() {
        return tipo;
    }

    public void setTipo(TipoCombustivel tipo) {
        this.tipo = tipo;
    }

    public Amostra getAmostra() {
        return amostra;
    }

    public void setAmostra(Amostra amostra) {
        this.amostra = amostra;
    }
}
