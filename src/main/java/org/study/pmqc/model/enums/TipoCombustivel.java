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
}
