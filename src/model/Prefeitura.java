package model;

import enums.Prioridade;

public class Prefeitura extends Ente {
    public Prefeitura(String codigo, String nome, Prioridade prioridade) {
        super(codigo, nome, prioridade);
    }

    @Override
    public String getSetorResponsavel() {
        return "Secretaria de Administração / RH";
    }

    @Override
    public boolean isAltaComplexidade() {
        return true; // Prefeitura tem muita folha, é complexo
    }
}