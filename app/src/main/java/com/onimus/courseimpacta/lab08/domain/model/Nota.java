package com.onimus.courseimpacta.lab08.domain.model;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Nota extends Entidade<Long> {

    private CharSequence titulo;
    private CharSequence observacao;
    private Long agendaInMillis;
    private Long agendaFeitaInMillis;

    private boolean agendada;
    private boolean feita;

    public Nota(Long id) {
        super(id);
    }

    public Nota(CharSequence titulo, CharSequence observacao) {
        this(null, titulo, observacao);
    }

    private Nota(Long id, CharSequence titulo, CharSequence observacao) {
        this(id);

        this.titulo = titulo;
        this.observacao = observacao;
        this.feita = false;
    }

    public String getTitulo() {
        return titulo != null ? titulo.toString() : null;
    }

    public void setTitulo(CharSequence titulo) {
        this.titulo = titulo;
    }

    public String getObservacao() {
        return observacao != null ? observacao.toString() : null;

    }

    public void setObservacao(CharSequence observacao) {
        this.observacao = observacao;
    }

    public Date getDataAgenda() {
        return new Date(agendaInMillis);
    }

    public Long getDataAgendaInMillis() {
        return agendaInMillis;
    }

    public void setDataAgenda(long agendaInMillis) {
        this.agendaInMillis = agendaInMillis;
    }

    public Date getDataAgendaFeita() {
        return new Date(agendaFeitaInMillis);
    }

    public void setDataAgendaFeita(Long agendaFeitaInMillis) {
        this.agendaFeitaInMillis = agendaFeitaInMillis;
    }

    public boolean isAgendada() {
        return agendada;
    }

    public void setAgendada(boolean agendada) {
        this.agendada = agendada;
    }

    public boolean isFeita() {
        return feita;
    }

    public void setFeita(boolean feita) {
        this.feita = feita;
    }

    @NotNull
    @Override
    public String toString() {

        return "Nota {" + "titulo='" + titulo + "\'" +
                ",observacao='" + observacao + "\'" +
                ",feita=" + feita +
                "}";
    }

}
