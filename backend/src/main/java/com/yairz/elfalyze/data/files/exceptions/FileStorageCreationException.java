package com.yairz.elfalyze.data.files.exceptions;

public class FileStorageCreationException extends RuntimeException {
    public FileStorageCreationException() {
    }

    public FileStorageCreationException(String message) {
        super(message);
    }

    public FileStorageCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageCreationException(Throwable cause) {
        super(cause);
    }
}
