package org.study.pmqc.model.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode
@ToString
@Getter
@Setter
@Entity
@Table(name = "estabelecimento")
public class Estabelecimento {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Column(name = "razao_social_posto")
    private String razaoSocialPosto;  //  Razão Social do Posto Revendedor de Combustível.

    @Column(name = "cnpj")
    private String cnpjPosto; // CNPJ do Posto Revendedor de Combustível.

    @Column(name = "distribuidora")
    private String distribuidora;

    @OneToOne(targetEntity = Endereco.class, cascade = CascadeType.ALL)
    private Endereco endereco;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "estabelecimento")
    private List<Amostra> amostras;


    public void addAmostras(Amostra amostra) {
        if (this.amostras == null) this.amostras = new ArrayList<>();
        this.amostras.add(amostra);
    }

}
