package com.yairz.elfalyze.exceptions.elf;

public class ElfServiceException extends Exception {
    public ElfServiceException() {
    }

    public ElfServiceException(String message) {
        super(message);
    }

    public ElfServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElfServiceException(Throwable cause) {
        super(cause);
    }
}
