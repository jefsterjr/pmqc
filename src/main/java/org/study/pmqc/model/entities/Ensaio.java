package org.study.pmqc.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.study.pmqc.model.enums.TipoEnsaio;

import javax.persistence.*;


@Entity
@Table(name = "ensaio")
public class Ensaio {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TipoEnsaio getTipo() {
        return tipo;
    }

    public void setTipo(TipoEnsaio tipo) {
        this.tipo = tipo;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getUnidadeEnsaio() {
        return unidadeEnsaio;
    }

    public void setUnidadeEnsaio(String unidadeEnsaio) {
        this.unidadeEnsaio = unidadeEnsaio;
    }

    public Boolean getConfome() {
        return confome;
    }

    public void setConfome(Boolean confome) {
        this.confome = confome;
    }

    public Amostra getAmostra() {
        return amostra;
    }

    public void setAmostra(Amostra amostra) {
        this.amostra = amostra;
    }
}
