package model;

import enums.Prioridade;

public class FundoMunicipal extends Ente {
    public FundoMunicipal(String codigo, String nome, Prioridade prioridade) {
        super(codigo, nome, prioridade);
    }

    @Override
    public String getSetorResponsavel() {
        return "Coordenação Financeira do Fundo";
    }

    @Override
    public boolean isAltaComplexidade() {
        return false; // Fundo isolado costuma ser simples
    }
}