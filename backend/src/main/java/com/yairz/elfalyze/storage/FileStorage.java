package com.yairz.elfalyze.storage;

import com.yairz.elfalyze.exceptions.storage.FileStorageException;

import java.io.InputStream;
import java.util.List;

public interface FileStorage {
    FileId store(InputStream content) throws FileStorageException;
    InputStream read(FileId fileId) throws FileStorageException;
    List<FileId> listFiles() throws FileStorageException;
    void delete(FileId fileId) throws FileStorageException;
}
