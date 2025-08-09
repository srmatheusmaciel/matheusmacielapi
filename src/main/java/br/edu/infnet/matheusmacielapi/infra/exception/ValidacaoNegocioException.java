package br.edu.infnet.matheusmacielapi.infra.exception;

public class ValidacaoNegocioException extends RuntimeException {
    public ValidacaoNegocioException(String message) {
        super(message);
    }
}
