package com.onimus.courseimpacta.lab08.domain.model;

import java.io.Serializable;
import java.util.Date;

public class Entidade<PK> implements Serializable {

    private PK id;
    private Long dataCriacao;
    private Long dataModificacao;

    Entidade(PK id) {
        super();

        this.id = id;
    }

    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }

    public boolean isNullId() {
        return id == null;
    }

    public Long getDataCriacaoInMillis() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao != null ? dataCriacao.getTime() : null;
    }

    public Long getDataModificacaoInMillis() {
        return dataModificacao;
    }

    public void setDataModificacao(Date dataModificacao) {
        this.dataModificacao = dataModificacao != null ? dataModificacao.getTime() : null;
    }

}
