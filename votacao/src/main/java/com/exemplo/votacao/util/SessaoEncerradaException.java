package com.exemplo.votacao.util;

public class SessaoEncerradaException extends RuntimeException {

    // Construtores
    public SessaoEncerradaException(String message) {
        super(message);
    }

    public SessaoEncerradaException(String message, Throwable cause) {
        super(message, cause);
    }
}