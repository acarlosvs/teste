package br.com.confitec.teste.commons.exceptions;

public class QuantidadeParcelasDiverventeException extends RuntimeException {

    public QuantidadeParcelasDiverventeException() {
        super("A quantidade de parcelas máxima não pode ser menor que a mínima.");
    }

}
