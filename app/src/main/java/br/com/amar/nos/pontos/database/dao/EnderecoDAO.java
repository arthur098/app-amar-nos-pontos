package br.com.amar.nos.pontos.database.dao;

import br.com.amar.nos.pontos.model.Endereco;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnderecoDAO {
    private static final String SEM_NUMERO = "S/N";
    private static Long lastId = 1L;
    
    private static final List<Endereco> enderecos = new ArrayList<>();

    public static List<Endereco> listar() {
        return enderecos;
    }

    public static void save(Endereco endereco) {
        endereco.setId(++lastId);
        String numero = endereco.getNumero();
        if(numero == null || numero.isEmpty()) {
            endereco.setNumero(SEM_NUMERO);
        }

        enderecos.add(endereco);
    }

    public static void update(Endereco endereco) {
        Endereco found = enderecos.stream().filter(p -> p.getId().equals(endereco.getId())).findFirst().orElse(null);
        String numero = endereco.getNumero();
        if(numero == null || numero.isEmpty()) {
            endereco.setNumero(SEM_NUMERO);
        }
        enderecos.set(enderecos.indexOf(found), endereco);
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

    public static void excluirPorIdPessoa(Long idPessoa) {
        enderecos.removeIf(endereco -> endereco.getIdPessoa().equals(idPessoa));
    }
}
