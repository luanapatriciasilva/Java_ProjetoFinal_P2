package model;

import enums.Prioridade;

public class Camara extends Ente {
    public Camara(String codigo, String nome, Prioridade prioridade) {
        super(codigo, nome, prioridade);
    }

    @Override
    public String getSetorResponsavel() {
        return "Diretoria Financeira / Tesouraria";
    }

    @Override
    public boolean isAltaComplexidade() {
        return false;
    }
}