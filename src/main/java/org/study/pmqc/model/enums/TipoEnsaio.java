package org.study.pmqc.model.enums;

public enum TipoEnsaio{

    COR("COR"),
    ASPECTO("ASPECTO"),
    CONDUTIVIDADE_ELETRICA("CONDUTIVIDADE ELÉTRICA"),
    TEOR_ALCOOLICO("TEOR ALCOÓLICO"),
    PH("POTENCIAL HIDROGENIÔNICO (PH)"),
    TEOR_HIDROCARBONETOS("TEOR DE HIDROCARBONETOS"),
    MASSSA_ESPECIFICA("MASSA ESPECÍFICA A 20°C");

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
