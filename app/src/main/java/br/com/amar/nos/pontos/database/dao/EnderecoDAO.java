package br.com.amar.nos.pontos.database.dao;

import br.com.amar.nos.pontos.model.Endereco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnderecoDAO {
    private static Long lastId = 1L;
    
    private static final List<Endereco> enderecos = new ArrayList<>();

    public static List<Endereco> listar() {
        return enderecos;
    }

    public static void save(Endereco contrato) {
        contrato.setId(++lastId);
        enderecos.add(contrato);
    }

    public static void update(Endereco contrato) {
        Endereco found = enderecos.stream().filter(p -> p.getId().equals(contrato.getId())).findFirst().orElse(null);
        enderecos.set(enderecos.indexOf(found), contrato);
    }

    public static void excluir(Long id) {
        enderecos.removeIf(Endereco -> Endereco.getId().equals(id));
    }

    public static Endereco buscarPorId(Long idEndereco) {
        return enderecos.stream().filter(Endereco -> Endereco.getId().equals(idEndereco)).findFirst().orElse(null);
    }

    public static List<Endereco> buscarPorIdPessoa(Long idPessoa) {
        return enderecos.stream().filter(endereco -> endereco.getIdPessoa().equals(idPessoa)).collect(Collectors.toList());
    }
}
