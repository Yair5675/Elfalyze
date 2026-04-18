package com.yairz.elfalyze.exceptions.elf;

public class ElfParsingException extends RuntimeException {
    public ElfParsingException() {
    }

    public ElfParsingException(String message) {
        super(message);
    }

    public ElfParsingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElfParsingException(Throwable cause) {
        super(cause);
    }
}
