package br.com.amar.nos.pontos.model;


import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class PessoaEndereco {
    @Embedded
    private Pessoa pessoa;

    @Relation(parentColumn = "id",
            entityColumn = "idPessoa")
    private List<Endereco> enderecos;

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Endereco> getEnderecos() {
        return enderecos;
    }

    public void setEnderecos(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }
}
