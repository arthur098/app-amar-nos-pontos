package br.com.amar.nos.pontos.model;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

public class Endereco {

    private Long id;
    private String bairro;
    private String logradouro;
    private String numero;
    private String complemento;
    private String municipio;
    private String estado;

    private Long idPessoa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public String getMunicipio() {
        return municipio;
    }

    public void setMunicipio(String municipio) {
        this.municipio = municipio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Long getIdPessoa() {
        return idPessoa;
    }

    public void setIdPessoa(Long idPessoa) {
        this.idPessoa = idPessoa;
    }

    @Ignore
    public String getEnderecoFormatado() {;
        return String.format("%s, %s, %s, %s, %s - %s", logradouro, numero, complemento, bairro, municipio, estado);
    }

    @NonNull
    @Override
    public String toString() {
        return logradouro;
    }
}
