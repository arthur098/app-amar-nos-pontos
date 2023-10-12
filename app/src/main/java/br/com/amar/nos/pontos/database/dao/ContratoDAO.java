package br.com.amar.nos.pontos.database.dao;

import br.com.amar.nos.pontos.model.Contrato;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContratoDAO {
    private static Long lastId = 1L;
    
    private static final List<Contrato> contratos = new ArrayList<>();

    public static List<Contrato> listar() {
        return contratos;
    }

    public static void save(Contrato contrato) {
        contrato.setId(++lastId);
        contratos.add(contrato);
    }

    public static void update(Contrato contrato) {
        Contrato found = contratos.stream().filter(p -> p.getId().equals(contrato.getId())).findFirst().orElse(null);
        contratos.set(contratos.indexOf(found), contrato);
    }

    public static void excluir(Long id) {
        contratos.removeIf(Contrato -> Contrato.getId().equals(id));
    }

    public static Contrato buscarPorId(Long idContrato) {
        return contratos.stream().filter(Contrato -> Contrato.getId().equals(idContrato)).findFirst().orElse(null);
    }

    public static List<Contrato> buscarPorIdPessoa(Long idPessoa) {
        return contratos.stream().filter(Contrato -> Contrato.getPessoa().getId().equals(idPessoa)).collect(Collectors.toList());
    }
}
