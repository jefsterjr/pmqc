package org.study.pmqc.model.dto;

import org.study.pmqc.model.enums.TipoEnsaio;

public class EnsaioTO {

    private TipoEnsaio ensaio;
    private String resultado;
    private String unidadeEnsaio;
    private Boolean confome;

    public EnsaioTO(final String ensaio, final String resultado, final String unidadeEnsaio, final String confome) {
        this.ensaio = TipoEnsaio.fromString(ensaio);
        this.resultado = resultado;
        this.unidadeEnsaio = unidadeEnsaio;
        setConfome(confome);
    }

    public TipoEnsaio getEnsaio() {
        return ensaio;
    }

    public void setEnsaio(final TipoEnsaio ensaio) {
        this.ensaio = ensaio;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    public String getUnidadeEnsaio() {
        return unidadeEnsaio;
    }

    public void setUnidadeEnsaio(String unidadeEnsaio) {
        this.unidadeEnsaio = unidadeEnsaio;
    }

    public Boolean getConfome() {
        return confome;
    }

    public void setConfome(Boolean confome) {
        this.confome = confome;
    }


    private void setConfome(final String confome) {
        this.confome = confome.equals("SIM");
    }
}
