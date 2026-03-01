package com.yairz.elfalyze.data.elf.exceptions;

public class Sha256ComputationException extends RuntimeException {
    public Sha256ComputationException() {
    }

    public Sha256ComputationException(String message) {
        super(message);
    }

    public Sha256ComputationException(String message, Throwable cause) {
        super(message, cause);
    }

    public Sha256ComputationException(Throwable cause) {
        super(cause);
    }
}
