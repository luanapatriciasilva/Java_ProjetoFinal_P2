package service;

import enums.Prioridade;
import enums.StatusEnvio;
import model.*;

import java.sql.Connection; // Connection do SQL
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import conexaoBd.Conexao;

public class SageService {


    private EnteService enteService = new EnteService();

    // 1. GERAR COMPETÊNCIA
    public void gerarCompetencia(String competencia) {
        System.out.println("\n>>> Processando abertura de competência: " + competencia + "...");

        List<Ente> todosOrgaos = enteService.listarEntes();

        if (todosOrgaos.isEmpty()) {
            System.out.println("[AVISO] Nenhum órgão encontrado no banco de dados.");
            return;
        }

        String sqlVerifica = "SELECT count(*) FROM acompanhamento WHERE competencia = ? AND codigo_ente = ?";
        String sqlInsert = "INSERT INTO acompanhamento (competencia, codigo_ente, status_envio, observacoes) VALUES (?, ?, ?, ?)";

        int contados = 0;


        try (Connection conn = Conexao.GeraConexao()) {

            for (Ente ente : todosOrgaos) {
                boolean existe = false;
                try (PreparedStatement stmtVerifica = conn.prepareStatement(sqlVerifica)) {
                    stmtVerifica.setString(1, competencia);
                    stmtVerifica.setString(2, ente.getCodigo());
                    ResultSet rs = stmtVerifica.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) existe = true;
                }

                if (!existe) {
                    try (PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert)) {
                        stmtInsert.setString(1, competencia);
                        stmtInsert.setString(2, ente.getCodigo());
                        stmtInsert.setString(3, StatusEnvio.nao_iniciado.name());
                        stmtInsert.setString(4, "");
                        stmtInsert.executeUpdate();
                        contados++;
                    }
                }
            }
            System.out.println("[SUCESSO] Competência gerada para " + contados + " novos registros.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 2. ATUALIZAR STATUS
    public void atualizarStatus(String competencia, String codigoEnte, StatusEnvio novoStatus, String observacao) {
        String sql = "UPDATE acompanhamento SET status_envio = ?, observacoes = ? WHERE competencia = ? AND codigo_ente = ?";

        try (Connection conn = Conexao.GeraConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, novoStatus.name());
            stmt.setString(2, observacao);
            stmt.setString(3, competencia);
            stmt.setString(4, codigoEnte);

            int linhas = stmt.executeUpdate();

            if (linhas > 0) {
                System.out.println(">> Status atualizado com sucesso para o código " + codigoEnte);
            } else {
                System.out.println("[ERRO] Registro não encontrado (verifique o mês e o código).");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3. PAINEL DE MONITORAMENTO
    public void exibirPainel(String competencia) {
        String sql = "SELECT a.*, e.nome, e.prioridade, e.tipo_ente " +
                "FROM acompanhamento a " +
                "JOIN ente e ON a.codigo_ente = e.codigo " +
                "WHERE a.competencia = ?";

        List<AcompanhamentoMensal> lista = new ArrayList<>();

        try (Connection conn = Conexao.GeraConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, competencia);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                lista.add(mapearAcompanhamento(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // IMPRESSÃO
        System.out.println("\n==========================================================================================================");
        System.out.println("                                  PAINEL DE MONITORAMENTO SAGE: " + competencia);
        System.out.println("==========================================================================================================");

        if (lista.isEmpty()) {
            System.out.println("                 [AVISO] Nenhum registro encontrado para a competência " + competencia);
        } else {
            System.out.printf("| %-8s | %-40s | %-15s | %-25s | %-20s |%n",
                    "CÓDIGO", "ENTE / TIPO", "PRIORIDADE", "STATUS ATUAL", "OBSERVAÇÕES");
            System.out.println("----------------------------------------------------------------------------------------------------------");

            for (AcompanhamentoMensal a : lista) {
                String tipoOrgao = a.getEnte().getClass().getSimpleName();
                String nomeFormatado = a.getEnte().getNome() + " (" + tipoOrgao + ")";
                String obs = (a.getObservacoes() == null) ? "" : a.getObservacoes();
                String statusVisual = formatarStatus(a.getStatusEnvio());

                System.out.printf("| %-8s | %-40s | %-15s | %-25s | %-20s |%n",
                        a.getEnte().getCodigo(),
                        limitString(nomeFormatado, 40),
                        a.getEnte().getPrioridade(),
                        statusVisual,
                        limitString(obs, 20)
                );
            }
        }
        System.out.println("==========================================================================================================\n");
    }

    // AUXILIARES
    private AcompanhamentoMensal mapearAcompanhamento(ResultSet rs) throws SQLException {
        String codigo = rs.getString("codigo_ente");
        String nome = rs.getString("nome");
        String prioridadeStr = rs.getString("prioridade");
        String tipoEnte = rs.getString("tipo_ente");

        Prioridade prioridade = Prioridade.valueOf(prioridadeStr);
        Ente ente;

        switch (tipoEnte) {
            case "Prefeitura": ente = new Prefeitura(codigo, nome, prioridade); break;
            case "Camara": ente = new Camara(codigo, nome, prioridade); break;
            case "FundoMunicipal": ente = new FundoMunicipal(codigo, nome, prioridade); break;
            case "Instituto": ente = new Instituto(codigo, nome, prioridade); break;
            default: ente = new Prefeitura(codigo, nome, prioridade);
        }

        AcompanhamentoMensal acomp = new AcompanhamentoMensal(rs.getString("competencia"), ente);
        acomp.setStatusEnvio(StatusEnvio.valueOf(rs.getString("status_envio")));
        acomp.setObservacoes(rs.getString("observacoes"));

        return acomp;
    }

    private String formatarStatus(StatusEnvio status) {
        switch (status) {
            case concluido: return "[OK] CONCLUÍDO";
            case erro_impeditivo: return "[X] ERRO CRÍTICO";
            case pendente_correcao: return "[!] CORREÇÃO";
            case pendente_dados: return "[?] FALTA DADOS";
            default: return status.toString();
        }
    }

    private String limitString(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }
}