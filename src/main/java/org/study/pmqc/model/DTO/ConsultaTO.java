package org.study.pmqc.model.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ConsultaTO {

    @JsonProperty("UF")
    private List<UFTO> uf;

    public List<UFTO> getUf() {
        return uf;
    }

    public void setUf(List<UFTO> uf) {
        this.uf = uf;
    }
}
