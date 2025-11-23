package model;
import enums.StatusEnvio;

public class AcompanhamentoMensal {
    private String competencia;
    private Ente ente;
    private StatusEnvio statusEnvio;
    private String observacoes;


    public AcompanhamentoMensal(String competencia, Ente ente) {
        this.competencia = competencia;
        this.ente = ente;
        this.statusEnvio = statusEnvio.nao_iniciado;
        this.observacoes = observacoes;
        this.observacoes = "";
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public StatusEnvio getStatusEnvio() {
        return statusEnvio;
    }

    public void setStatusEnvio(StatusEnvio statusEnvio) {
        this.statusEnvio = statusEnvio;
    }

    public Ente getEnte() {
        return ente;
    }

    public void setEnte(Ente ente) {
        this.ente = ente;
    }

    public String getCompetencia() {
        return competencia;
    }

    public void setCompetencia(String competencia) {
        this.competencia = competencia;
    }


    @Override
    public String toString() {
        return "AcompanhamentoMensal{" +
                ", ente=" + ente.getNome() +
                ", statusEnvio=" + statusEnvio +
                ", competencia='" + competencia + '\'' +
                "observacoes='" + observacoes + '\'' +
                '}';
    }
}
