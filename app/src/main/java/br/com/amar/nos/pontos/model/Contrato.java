package br.com.amar.nos.pontos.model;


import androidx.room.Ignore;
import br.com.amar.nos.pontos.enumerator.EnumStatusContrato;

import java.math.BigDecimal;

//@Entity
public class Contrato {


    private Long id;

    private String produto;

    private EnumStatusContrato status;

    private BigDecimal valor;

    private BigDecimal valorPago;

    private String observacao;
    @Ignore
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public EnumStatusContrato getStatus() {
        return status;
    }

    public void setStatus(EnumStatusContrato status) {
        this.status = status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
