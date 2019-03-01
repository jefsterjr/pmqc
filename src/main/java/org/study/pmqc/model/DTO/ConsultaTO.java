package org.study.pmqc.model.DTO;

import org.study.pmqc.model.enums.TipoCombustivel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ConsultaTO {

    private LocalDate dataColeta; // Data no formato ISSO 8601 (AAAA-MM-DD) que a coleta de combustível foi realizada.
    private String idNumeric;  // Identificador único das amostras.
    private TipoCombustivel grupoProduto;  // TEXTO Identifica a família de combustível (Gasolina, Diesel ou Etanol).
    private String produto;  //   Identifica o produto específico de uma dada família de combustível. Por exemplo, para Gasolina há os produtos: Gasolina C Comum, Gasolina C Aditivada e Gasolina C Premium.

    private String latitude; // Latitude do Posto Revendedor de Combustível
    private String longitude; // Longitude do Posto Revendedor de Combustível

    private String RazaoSocialPosto;  //  Razão Social do Posto Revendedor de Combustível.
    private String CnpjPosto; // CNPJ do Posto Revendedor de Combustível.
    private String distribuidora; //; Distribuidora associada ao Posto Revendedor de Combustível

    private String logradouro; // Endereço do posto de revenda de combustíveis
    private String Complemento; // Complemento do endereço
    private String bairro;

    private List<EnsaioTO> ensaios;
    private String uf;
    private String regiao;
    private String municipio;

    public LocalDate getDataColeta() {
        return dataColeta;
    }

    public void setDataColeta(LocalDate dataColeta) {
        this.dataColeta = dataColeta;
    }

    public String getIdNumeric() {
        return idNumeric;
    }

    public void setIdNumeric(String idNumeric) {
        this.idNumeric = idNumeric;
    }

    public TipoCombustivel getGrupoProduto() {
        return grupoProduto;
    }

    public void setGrupoProduto(TipoCombustivel grupoProduto) {
        this.grupoProduto = grupoProduto;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<EnsaioTO> getEnsaios() {
        return ensaios;
    }

    public void setEnsaios(List<EnsaioTO> ensaios) {
        this.ensaios = ensaios;
    }

    public void addEnsaio(EnsaioTO ensaio) {
        if(this.ensaios == null) this.ensaios = new ArrayList<>();
        this.ensaios.add(ensaio);
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getRazaoSocialPosto() {
        return RazaoSocialPosto;
    }

    public void setRazaoSocialPosto(String razaoSocialPosto) {
        RazaoSocialPosto = razaoSocialPosto;
    }

    public String getCnpjPosto() {
        return CnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        CnpjPosto = cnpjPosto;
    }

    public String getDistribuidora() {
        return distribuidora;
    }

    public void setDistribuidora(String distribuidora) {
        this.distribuidora = distribuidora;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }
}
