package model;

import enums.Prioridade;
// 1. Importar a Interface
import interfaces.IOrgaoPublico;

// 2. Implementar a Interface
public abstract class Ente implements IOrgaoPublico {

    private String codigo;
    private String nome;
    private Prioridade prioridade;

    public Ente() {
    }

    public Ente(String codigo, String nome, Prioridade prioridade) {
        this.codigo = codigo;
        this.nome = nome;
        this.prioridade = prioridade;
    }

    // A classe Ente "passa a bola" da interface para os filhos implementarem.
    // Mantemos como abstract para obrigar os filhos a definirem.
    @Override
    public abstract String getSetorResponsavel();

    @Override
    public abstract boolean isAltaComplexidade();

    // Getters e Setters normais...
    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    @Override
    public String toString() {
        return "Ente{" + "codigo='" + codigo + "', nome='" + nome + "'}";
    }
}