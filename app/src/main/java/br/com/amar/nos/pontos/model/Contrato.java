package br.com.amar.nos.pontos.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import br.com.amar.nos.pontos.enumerator.EnumStatusContrato;

@Entity(foreignKeys = {
        @ForeignKey(entity = Endereco.class,
        parentColumns = "id",
        childColumns = "idEndereco"),
        @ForeignKey(entity = Pessoa.class,
        parentColumns = "id",
        childColumns = "idPessoa")
})
public class Contrato {

    @PrimaryKey(autoGenerate = true)
    private Long id;

    private String produto;

    private String descricaoProduto;

    private EnumStatusContrato status;

    private Double valor;

    private Double valorPago;

    private String observacao;

    private Long idPessoa;

    private Long idEndereco;

    @Ignore
    private Pessoa pessoa;

    @Ignore
    private Endereco endereco;

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

    public String getDescricaoProduto() {
        return descricaoProduto;
    }

    public void setDescricaoProduto(String descricaoProduto) {
        this.descricaoProduto = descricaoProduto;
    }

    public EnumStatusContrato getStatus() {
        return status;
    }

    public void setStatus(EnumStatusContrato status) {
        this.status = status;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    public Long getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(Long idEndereco) {
        this.idEndereco = idEndereco;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }
}
