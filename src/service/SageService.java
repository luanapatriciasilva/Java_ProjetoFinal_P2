package service;

import enums.Prioridade;
import enums.StatusEnvio;
import enums.TipoOrgao;
import model.AcompanhamentoMensal;
import model.Ente;
import repository.AcompanhamentoRepository;
import repository.EnteRepository;

import java.util.List;

public class SageService {


    private final EnteRepository enteRepository;
    private final AcompanhamentoRepository acompanhamentoRepository;

    public SageService(EnteRepository enteRepo, AcompanhamentoRepository acompRepo) {
        this.enteRepository = enteRepo;
        this.acompanhamentoRepository = acompRepo;
    }

    // --- REGRA DE NEGÓCIO 1: CADASTRO DE ENTES ---
    public void cadastrarEnte(String codigo, String nome, TipoOrgao tipo, Prioridade prioridade) {

        Ente novo = new Ente(codigo, nome, tipo, prioridade);
        enteRepository.salvar(novo);
        System.out.println("[SISTEMA] Ente cadastrado com sucesso: " + novo.getNome());
    }

    // --- REGRA DE NEGÓCIO 2: GERAR COMPETÊNCIA (BATCH) ---
    // Cria automaticamente o registro de status para TODOS os órgãos ativos
    public void gerarCompetencia(String mesReferencia) {
        System.out.println("\n>>> Processando abertura de competência: " + mesReferencia + "...");

        List<Ente> todosOrgaos = enteRepository.listarTodos();

        if (todosOrgaos.isEmpty()) {
            System.out.println("[AVISO] Nenhum órgão cadastrado. Cadastre antes de gerar o mês.");
            return;
        }

        int contados = 0;
        for (Ente ente : todosOrgaos) {
            // Verifica se já existe para não duplicar (regra de segurança)
            AcompanhamentoMensal existente = acompanhamentoRepository.buscarPorEnteEMes(ente.getNome(), mesReferencia);

            if (existente == null) {
                AcompanhamentoMensal novo = new AcompanhamentoMensal(mesReferencia, ente);
                acompanhamentoRepository.salvar(novo);
                contados++;
            }
        }
        System.out.println("[SUCESSO] Competência gerada para " + contados + " órgãos. Status inicial: NAO_INICIADO.");
    }

    // --- REGRA DE NEGÓCIO 3: ATUALIZAR STATUS ---
    public void atualizarStatus(String mesRef, String nomeEnte, StatusEnvio novoStatus, String observacao) {
        AcompanhamentoMensal registro = acompanhamentoRepository.buscarPorEnteEMes(nomeEnte, mesRef);

        if (registro != null) {
            registro.setStatusEnvio(novoStatus);

            // Observação é opcional, mas se vier preenchida, atualizamos
            if (observacao != null && !observacao.isEmpty()) {
                registro.setObservacoes(observacao);
            }
            System.out.println(">> Atualizado: " + nomeEnte + " -> " + novoStatus);
        } else {
            System.out.println("[ERRO] Registro não encontrado para " + nomeEnte + " em " + mesRef);
        }
    }

    // --- REGRA DE NEGÓCIO 4: PAINEL DE MONITORAMENTO ---
    public void exibirPainel(String mesRef) {
        List<AcompanhamentoMensal> lista = acompanhamentoRepository.buscarPorReferencia(mesRef);

        System.out.println("\n=================================================");
        System.out.println(" PAINEL DE ACOMPANHAMENTO SAGE - " + mesRef);
        System.out.println("=================================================");

        if (lista.isEmpty()) {
            System.out.println("Nenhum registro encontrado.");
        } else {
            for (AcompanhamentoMensal a : lista) {
                System.out.println(a); // Vai usar o toString() bonito que fizemos no Model
            }
        }
        System.out.println("=================================================\n");
    }
}