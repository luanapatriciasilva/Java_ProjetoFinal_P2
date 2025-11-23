package repository;

import model.Ente;

public class EnteRepository extends RepositorioEmMemoria<Ente> {

    // Método exclusivo para buscar cliente pelo código (Ex: 201001)
    public Ente buscarPorCodigo(String codigo) {
        for (Ente e : lista) {
            if (e.getCodigo().equals(codigo)) {
                return e;
            }
        }
        return null; // Retorna nulo se não achar
    }

    // Método auxiliar para buscar por nome (útil na simulação)
    public Ente buscarPorNome(String nome) {
        for (Ente e : lista) {
            if (e.getNome().equalsIgnoreCase(nome)) {
                return e;
            }
        }
        return null;
    }
}