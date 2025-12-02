package service;

import enums.Prioridade;
import model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import conexaoBd.Conexao;

public class EnteService {

    // 1. CADASTRAR
    public void cadastrarEnte(Ente ente) {
        String sql = "INSERT INTO ente (codigo, nome, prioridade, tipo_ente) VALUES (?, ?, ?, ?)";
        try (Connection conn = Conexao.GeraConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, ente.getCodigo());
            stmt.setString(2, ente.getNome());
            stmt.setString(3, ente.getPrioridade().name());
            stmt.setString(4, ente.getClass().getSimpleName());
            stmt.executeUpdate();
            System.out.println("Ente cadastrado com sucesso no banco!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. LISTAR
    public List<Ente> listarEntes() {
        List<Ente> lista = new ArrayList<>();
        String sql = "SELECT * FROM ente";
        try (Connection conn = Conexao.GeraConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(mapearEnte(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // 3. ATUALIZAR
    public void atualizarEnte(String codigo, String novoNome) {
        String sql = "UPDATE ente SET nome = ? WHERE codigo = ?";
        try (Connection conn = Conexao.GeraConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, novoNome);
            stmt.setString(2, codigo);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) System.out.println("Atualizado com sucesso!");
            else System.out.println("Ente não encontrado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. DELETAR
    public void deletarEnte(String codigo) {
        String sql = "DELETE FROM ente WHERE codigo = ?";
        try (Connection conn = Conexao.GeraConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, codigo);
            int linhas = stmt.executeUpdate();
            if (linhas > 0) System.out.println("Deletado com sucesso!");
            else System.out.println("Ente não encontrado.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // AUXILIAR
    private Ente mapearEnte(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo");
        String nome = rs.getString("nome");
        String prioridadeStr = rs.getString("prioridade");
        String tipoEnte = rs.getString("tipo_ente");

        Prioridade prioridade = Prioridade.valueOf(prioridadeStr);

        switch (tipoEnte) {
            case "Prefeitura": return new Prefeitura(codigo, nome, prioridade);
            case "Camara": return new Camara(codigo, nome, prioridade);
            case "Instituto": return new Instituto(codigo, nome, prioridade);


            case "FundoMunicipal": return new FundoMunicipal(codigo, nome, prioridade);


            case "FundoAssistencia": return new FundoMunicipal(codigo, nome, prioridade);

            default: return new Prefeitura(codigo, nome, prioridade);
        }
    }
}