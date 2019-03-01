package org.study.pmqc.model.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private Integer id;

    @Column(name = "razao_social_posto")
    private String razaoSocialPosto;  //  Razão Social do Posto Revendedor de Combustível.

    @Column(name = "cnpj")
    private String cnpjPosto; // CNPJ do Posto Revendedor de Combustível.

    @Column(name = "distribuidora")
    private String distribuidora;

    @OneToOne(targetEntity = Endereco.class, cascade = CascadeType.ALL, mappedBy = "estabelecimento")
    private Endereco endereco;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "estabelecimento")
    private List<Amostra> amostras;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRazaoSocialPosto() {
        return razaoSocialPosto;
    }

    public void setRazaoSocialPosto(String razaoSocialPosto) {
        this.razaoSocialPosto = razaoSocialPosto;
    }

    public String getCnpjPosto() {
        return cnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        this.cnpjPosto = cnpjPosto;
    }

    public String getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(String distribuidora) {
        this.distribuidora = distribuidora;
    }

    public List<Amostra> getAmostras() {
        return amostras;
    }

    public void setAmostras(List<Amostra> amostras) {
        this.amostras = amostras;
    }

    public void addAmostras(Amostra amostra) {
        if (this.amostras == null) this.amostras = new ArrayList<>();
        this.amostras.add(amostra);
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
