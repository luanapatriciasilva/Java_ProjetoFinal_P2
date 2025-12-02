package controller;

import model.Ente;
import service.EnteService;

import java.util.List;

public class EnteController {

    // Instancia o serviço que contém a lógica do Banco de Dados
    private EnteService enteService = new EnteService();

    // 1. CADASTRAR
    public void cadastrar(Ente e) {
        enteService.cadastrarEnte(e);
    }

    // 2. LISTAR
    public List<Ente> listar() {
        return enteService.listarEntes();
    }

    // 3. ATUALIZAR
    public void atualizar(String codigo, String novoNome) {
        enteService.atualizarEnte(codigo, novoNome);
    }

    // 4. DELETAR
    public void deletar(String codigo) {
        enteService.deletarEnte(codigo);
    }
}