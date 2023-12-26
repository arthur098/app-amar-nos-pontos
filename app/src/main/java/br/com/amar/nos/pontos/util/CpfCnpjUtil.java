package br.com.amar.nos.pontos.util;

public class CpfCnpjUtil {
    public static String formatar(String cpfCnpj) {
        return cpfCnpj.length() > 11 ? formataCnpj(cpfCnpj) : formataCpf(cpfCnpj);
    }

    public static String formataCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    public static String formataCnpj(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }
}
