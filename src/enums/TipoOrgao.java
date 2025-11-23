package enums;

public enum TipoOrgao {
    PREFEITURA("PM"),
    CAMARA_MUNICIPAL("CM"),
    INSTITUTO_PREVIDENCIA("IPM"),
    FUNDO_SAUDE("FMS"),
    FUNDO_ASSISTENCIA_SOCIAL("FMAS"),
    INSTITUTO_PREV_ASSISTENCIA("IPAM"),
    INSTITUTO_PREV_SERVIDORES("IPSMB"),
    INSTITUTO_MUNICIPAL("IMP");

    private final String sigla;

    // Construtor do Enum
    TipoOrgao(String sigla) {
        this.sigla = sigla;
    }

    public String getSigla() {
        return sigla;
    }
}