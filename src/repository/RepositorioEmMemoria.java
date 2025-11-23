package repository;

import java.util.ArrayList;
import java.util.List;

// <T> significa "Tipo Genérico". Pode ser Ente, Acompanhamento, ou qualquer coisa.
public abstract class RepositorioEmMemoria<X> {

    protected List<X> lista = new ArrayList<>();

    public void salvar(X objeto) {
        lista.add(objeto);
    }

    public List<X> listarTodos() {
        return lista;
    }
}