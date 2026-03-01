package com.yairz.elfalyze.exceptions.storage;

public class FileStorageException extends Exception {
    public FileStorageException() {
    }

    public FileStorageException(String message) {
        super(message);
    }

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileStorageException(Throwable cause) {
        super(cause);
    }
}
