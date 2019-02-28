package org.study.pmqc.model.enums;

public enum TipoEnsaio {

    COR("COR"),ASPECTO("ASPECTO"),CONDUTIVIDADE_ELETRICA("CONDUTIVIDADE ELETRICA A 20ºC"),TEOR_ALCOLICO("TEOR ALCOLICO"),PH("PH"),TEOR_HIDROCARBONETOS("TEOR HIDROCARBONETOS");

    private final String ensaio;

    TipoEnsaio(final String ensaio) {
        this.ensaio = ensaio;
    }

    public String getEnsaio() {
        return ensaio;
    }


}
