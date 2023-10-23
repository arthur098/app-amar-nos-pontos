package br.com.amar.nos.pontos.enumerator;

public enum EnumStatusContrato {
    PAGAMENTO_PENDENTE("Pagamento pendente"),
    PAGO("Pago");

    private final String descricao;

    EnumStatusContrato(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return this.descricao;
    }
}
