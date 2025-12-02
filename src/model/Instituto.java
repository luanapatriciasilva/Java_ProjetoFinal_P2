package model;

import enums.Prioridade;

public class Instituto extends Ente {
    public Instituto(String codigo, String nome, Prioridade prioridade) {
        super(codigo, nome, prioridade);
    }

    @Override
    public String getSetorResponsavel() {
        return "Diretoria de Previdência";
    }

    @Override
    public boolean isAltaComplexidade() {
        return true; // Regras de aposentadoria são complexas
    }
}