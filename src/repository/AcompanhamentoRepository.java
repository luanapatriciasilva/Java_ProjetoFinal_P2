package repository;

import model.AcompanhamentoMensal;
import java.util.ArrayList;
import java.util.List;

public class AcompanhamentoRepository extends RepositorioEmMemoria<AcompanhamentoMensal> {

    // Retorna todos os envios de um mês específico (Ex: Outubro/2025)
    public List<AcompanhamentoMensal> buscarPorReferencia(String mesReferencia) {
        List<AcompanhamentoMensal> resultado = new ArrayList<>();

        for (AcompanhamentoMensal a : lista) {
            if (a.getCompetencia().equals(mesReferencia)) {
                resultado.add(a);
            }
        }
        return resultado;
    }

    // Busca um acompanhamento específico para poder atualizar o status
    public AcompanhamentoMensal buscarPorEnteEMes(String nomeEnte, String mesReferencia) {
        for (AcompanhamentoMensal a : lista) {
            if (a.getCompetencia().equals(mesReferencia) &&
                    a.getEnte().getNome().equalsIgnoreCase(nomeEnte)) {
                return a;
            }
        }
        return null;
    }
}