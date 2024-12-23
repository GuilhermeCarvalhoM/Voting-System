package com.exemplo.votacao.util;

public class VotoJaRegistradoException extends RuntimeException {

    // Construtores
    public VotoJaRegistradoException(String message) {
        super(message);
    }

    public VotoJaRegistradoException(String message, Throwable cause) {
        super(message, cause);
    }
}