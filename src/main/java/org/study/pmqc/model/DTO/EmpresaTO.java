package org.study.pmqc.model.DTO;

public class EmpresaTO {

    private String RazaoSocialPosto;  //  Razão Social do Posto Revendedor de Combustível.
    private String CnpjPosto; // CNPJ do Posto Revendedor de Combustível.
    private String distribuidora; //; Distribuidora associada ao Posto Revendedor de Combustível

    public EmpresaTO(String razaoSocialPosto, String cnpjPosto, String distribuidora) {
        RazaoSocialPosto = razaoSocialPosto;
        CnpjPosto = cnpjPosto;
        this.distribuidora = distribuidora;
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
}
