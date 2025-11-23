package model;

import enums.Prioridade;
import enums.TipoOrgao;

public class Ente {
    private String codigo;
    private String nome;
    private TipoOrgao tipoOrgao;
    private Prioridade prioridade;


    public Ente(String codigo, String nome, TipoOrgao tipo, Prioridade prioridade) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Prioridade getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(Prioridade prioridade) {
        this.prioridade = prioridade;
    }

    public TipoOrgao getTipoOrgao() {
        return tipoOrgao;
    }

    public void setTipoOrgao(TipoOrgao tipoOrgao) {
        this.tipoOrgao = tipoOrgao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "model.Ente{" +
                "prioridade=" + prioridade +
                '}';
    }
}
