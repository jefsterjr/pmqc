package org.study.pmqc.model.DTO;

public class EnderecoTO {

    private String logradouro; // Endereço do posto de revenda de combustíveis
    private String Complemento; // Complemento do endereço
    private String bairro; // Bairro

    public EnderecoTO(String logradouro, String complemento, String bairro) {
        this.logradouro = logradouro;
        Complemento = complemento;
        this.bairro = bairro;
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
