package br.com.amar.nos.pontos.model;


import androidx.room.Ignore;

import java.math.BigDecimal;

//@Entity
public class Contrato {


    private Long id;

    //TODO status
    private BigDecimal valor;

    @Ignore
    private Pessoa pessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
