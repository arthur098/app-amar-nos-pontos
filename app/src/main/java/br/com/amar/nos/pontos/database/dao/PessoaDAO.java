package br.com.amar.nos.pontos.database.dao;

import br.com.amar.nos.pontos.model.Pessoa;

import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    private static Long lastId = 1L;
    private static final List<Pessoa> pessoas = new ArrayList<>();

    public static List<Pessoa> listar() {
        return pessoas;
    }

    public static void save(Pessoa... pessoaAddList) {
        for (Pessoa pessoa:pessoaAddList) {
            pessoa.setId(++lastId);
            PessoaDAO.pessoas.add(pessoa);
        }
    }

    public static void update(Pessoa pessoa) {
        Pessoa found = pessoas.stream().filter(p -> p.getId().equals(pessoa.getId())).findFirst().orElse(null);
        pessoas.set(pessoas.indexOf(found), pessoa);
    }

    public static void excluir(Long id) {
        pessoas.removeIf(pessoa -> pessoa.getId().equals(id));
    }

    public static Pessoa buscarPorId(Long idPessoa) {
        return pessoas.stream().filter(pessoa -> pessoa.getId().equals(idPessoa)).findFirst().orElse(null);
    }
}
