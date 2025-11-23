import enums.Prioridade;
import enums.StatusEnvio;
import enums.TipoOrgao;
import repository.AcompanhamentoRepository;
import repository.EnteRepository;
import service.SageService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // 1. INICIALIZAÇÃO DO SISTEMA (Manual, sem Spring)
        // Instanciamos os Repositórios (banco de dados em memória)
        EnteRepository enteRepo = new EnteRepository();
        AcompanhamentoRepository acompRepo = new AcompanhamentoRepository();

        // Instanciamos o Service e "injetamos" os repositórios nele
        SageService service = new SageService(enteRepo, acompRepo);

        // Scanner para ler o que o usuário digita
        Scanner scanner = new Scanner(System.in);

        System.out.println("========================================");
        System.out.println("      SAGE - SISTEMA DE GESTÃO E-SOCIAL");
        System.out.println("========================================");


        // Aqui simulamos o cadastro prévio dos seus clientes
        service.cadastrarEnte("201001", "ÁGUA BRANCA", TipoOrgao.PREFEITURA, Prioridade.urgente);
        service.cadastrarEnte("301001", "ÁGUA BRANCA", TipoOrgao.INSTITUTO_PREVIDENCIA, Prioridade.normal);
        service.cadastrarEnte("601001", "ÁGUA BRANCA", TipoOrgao.FUNDO_SAUDE, Prioridade.intermediaria);

        service.cadastrarEnte("101012", "ARARA", TipoOrgao.CAMARA_MUNICIPAL, Prioridade.normal);
        service.cadastrarEnte("201012", "ARARA", TipoOrgao.PREFEITURA, Prioridade.urgente);

        System.out.println("[INIT] Carga completa! Sistema pronto.");

        // 3. MENU INTERATIVO
        int opcao = -1;
        while (opcao != 0) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Gerar Nova Competência (Virada de Mês)");
            System.out.println("2. Atualizar Status de Envio");
            System.out.println("3. Exibir Painel de Monitoramento");
            System.out.println("0. Sair");
            System.out.print("Digite a opção desejada: ");

            // Tratamento simples para evitar erro se digitar letra
            if (scanner.hasNextInt()) {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpar o buffer do teclado
            } else {
                scanner.next(); // Descarta entrada inválida
                opcao = -1;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Informe o Mês/Ano (ex: 10/2025): ");
                    String mes = scanner.nextLine();
                    service.gerarCompetencia(mes);

                    break;

                case 2:
                    System.out.println("\n--- ATUALIZAÇÃO DE STATUS ---");
                    System.out.print("Mês Referência (ex: 10/2025): ");
                    String mesRef = scanner.nextLine();

                    System.out.print("Nome do Ente (ex: ARARA): ");
                    String nomeEnte = scanner.nextLine();

                    System.out.println("Escolha o novo Status:");
                    System.out.println("1- PENDENTE_DADOS | 2- PENDENTE_ENVIO | 3- CONCLUIDO | 4- ERRO");
                    int statusOp = scanner.nextInt();
                    scanner.nextLine(); // Limpar buffer

                    StatusEnvio novoStatus = StatusEnvio.nao_iniciado;
                    if (statusOp == 1) novoStatus = StatusEnvio.pendente_dados;
                    else if (statusOp == 2) novoStatus = StatusEnvio.pendente_correcao;
                    else if (statusOp == 3) novoStatus = StatusEnvio.concluido;
                    else if (statusOp == 4) novoStatus = StatusEnvio.erro_impeditivo;

                    System.out.print("Observação (Opcional, ENTER para pular): ");
                    String obs = scanner.nextLine();

                    service.atualizarStatus(mesRef, nomeEnte, novoStatus, obs);
                    break;

                case 3:
                    System.out.print("Qual mês deseja visualizar? (ex: 10/2025): ");
                    String mesPainel = scanner.nextLine();
                    service.exibirPainel(mesPainel);
                    break;

                case 0:
                    System.out.println("Encerrando sistema SAGE. Até logo!");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }
}