package org.study.pmqc.model.enums;

public enum TipoCombustivel {

    GASOLINA("GASOLINA"), ETANOL("ETANOL"), DIESEL("ÓLEO DIESEL");

    private String combustivel;

    TipoCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public String getCombustivel() {
        return combustivel;
    }

    public static TipoCombustivel fromString(String text) {
        for (TipoCombustivel b : TipoCombustivel.values()) {
            if (b.combustivel.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
