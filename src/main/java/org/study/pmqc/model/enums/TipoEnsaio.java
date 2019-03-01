package org.study.pmqc.model.enums;

public enum TipoEnsaio{

    COR("COR"),ASPECTO("ASPECTO"),CONDUTIVIDADE_ELETRICA("CONDUTIVIDADE ELÉTRICA"),TEOR_ALCOLICO("TEOR ALCOLICO"),PH("PH"),TEOR_HIDROCARBONETOS("TEOR HIDROCARBONETOS");

    private final String ensaio;

    TipoEnsaio(final String ensaio) {
        this.ensaio = ensaio;
    }

    public String getEnsaio() {
        return ensaio;
    }

    public static TipoEnsaio fromString(String text) {
        for (TipoEnsaio b : TipoEnsaio.values()) {
            if (b.ensaio.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }


}
