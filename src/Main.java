import controller.EnteController;
import enums.Prioridade;
import enums.StatusEnvio;
import model.*;
import service.SageService;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // --- 1. INICIALIZAÇÃO ---
        EnteController enteController = new EnteController();
        SageService sageService = new SageService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("      SAGE - GESTÃO E-SOCIAL (MYSQL)");
        System.out.println("========================================");

        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Cadastrar Novo Órgão (Ente)");
            System.out.println("2. Gerar Competência (Virada de Mês)");
            System.out.println("3. Atualizar Status de Envio");
            System.out.println("4. Exibir Painel de Monitoramento (Por Mês)");
            System.out.println("5. Listar Órgãos Cadastrados (Relatório)");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");

            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar buffer
            } else {
                scanner.next();
                continue;
            }

            try {
                switch (opcao) {
                    case 1: // CADASTRO
                        System.out.println("\n--- 1. NOVO CADASTRO ---");
                        System.out.print("Código (ex: 201001): "); String codigo = scanner.nextLine();
                        System.out.print("Nome (ex: PREFEITURA X): "); String nome = scanner.nextLine();

                        System.out.println("Selecione o Tipo:");
                        System.out.println("1- Prefeitura | 2- Camara | 3- Fundo Municipal | 4- Instituto");
                        int tipoOp = scanner.nextInt();

                        System.out.println("Selecione a Prioridade:");
                        System.out.println("1- Normal | 2- Intermediaria | 3- Urgente");
                        int prioOp = scanner.nextInt(); scanner.nextLine();

                        Prioridade prioridade = Prioridade.normal;
                        if (prioOp == 2) prioridade = Prioridade.intermediaria;
                        if (prioOp == 3) prioridade = Prioridade.urgente;

                        Ente novoEnte = null;
                        switch (tipoOp) {
                            case 1: novoEnte = new Prefeitura(codigo, nome, prioridade); break;
                            case 2: novoEnte = new Camara(codigo, nome, prioridade); break;
                            case 3: novoEnte = new FundoMunicipal(codigo, nome, prioridade); break;
                            case 4: novoEnte = new Instituto(codigo, nome, prioridade); break;
                            default: System.out.println("[!] Tipo inválido."); continue;
                        }

                        enteController.cadastrar(novoEnte);
                        break;

                    case 2: // GERAR MÊS
                        System.out.println("\n--- 2. GERAR COMPETÊNCIA ---");
                        System.out.print("Informe o Mês/Ano (ex: 11/2025): ");
                        String mes = scanner.nextLine();
                        sageService.gerarCompetencia(mes);
                        break;

                    case 3: // ATUALIZAR STATUS
                        System.out.println("\n--- 3. ATUALIZAR STATUS ---");
                        System.out.print("Mês Referência (ex: 11/2025): "); String mesRef = scanner.nextLine();

                        // Dica visual para o usuário
                        System.out.print("Código do Ente (Use a Opção 5 para consultar): "); String codEnte = scanner.nextLine();

                        System.out.println("Novo Status:");
                        System.out.println("1- Pendente Dados | 2- Pendente Correção | 3- Concluído | 4- Erro");
                        int stOp = scanner.nextInt(); scanner.nextLine();

                        StatusEnvio novoStatus = StatusEnvio.nao_iniciado;
                        if (stOp == 1) novoStatus = StatusEnvio.pendente_dados;
                        if (stOp == 2) novoStatus = StatusEnvio.pendente_correcao;
                        if (stOp == 3) novoStatus = StatusEnvio.concluido;
                        if (stOp == 4) novoStatus = StatusEnvio.erro_impeditivo;

                        System.out.print("Observação (Opcional): "); String obs = scanner.nextLine();
                        sageService.atualizarStatus(mesRef, codEnte, novoStatus, obs);
                        break;

                    case 4: // PAINEL MENSAL
                        System.out.print("\nQual mês deseja visualizar? (ex: 11/2025): ");
                        String mesPainel = scanner.nextLine();
                        sageService.exibirPainel(mesPainel);
                        break;

                    case 5: // LISTAR GERAL (Relatório Rico)
                        List<Ente> lista = enteController.listar();
                        System.out.println("\n===================================================================================================");
                        System.out.println("                                      RELATÓRIO DE ENTIDADES");
                        System.out.println("===================================================================================================");

                        if (lista.isEmpty()) {
                            System.out.println("Nenhum órgão cadastrado no banco.");
                        } else {
                            // Cabeçalho da Tabela
                            System.out.printf("| %-8s | %-25s | %-35s | %-12s |%n",
                                    "CÓDIGO", "NOME (TIPO)", "SETOR RESPONSÁVEL", "COMPLEXIDADE");
                            System.out.println("---------------------------------------------------------------------------------------------------");

                            for (Ente e : lista) {
                                String tipo = e.getClass().getSimpleName();
                                String nomeCompleto = e.getNome() + " (" + tipo + ")";

                                // Polimorfismo em ação:
                                String complexidade = e.isAltaComplexidade() ? "ALTA" : "BAIXA";
                                String setor = e.getSetorResponsavel();

                                System.out.printf("| %-8s | %-25s | %-35s | %-12s |%n",
                                        e.getCodigo(),
                                        limitString(nomeCompleto, 25),
                                        limitString(setor, 35),
                                        complexidade);
                            }
                        }
                        System.out.println("===================================================================================================\n");
                        break;

                    case 0:
                        System.out.println("Encerrando sistema... Até logo!");
                        break;

                    default:
                        System.out.println("Opção inválida!");
                }
            } catch (Exception e) {
                System.out.println("[ERRO CRÍTICO] " + e.getMessage());
                e.printStackTrace();
            }
        }
        scanner.close();
    }

    // --- MÉTODOS AUXILIARES ---


    private static String limitString(String text, int max) {
        if (text == null) return "";
        if (text.length() <= max) return text;
        return text.substring(0, max - 3) + "...";
    }

}