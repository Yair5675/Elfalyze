package com.yairz.elfalyze.storage.creators;

import com.yairz.elfalyze.storage.FileStorage;
import com.yairz.elfalyze.storage.LocalFileStorage;
import com.yairz.elfalyze.exceptions.storage.FileStorageCreationException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class LocalFileStorageCreator implements FileStorageCreator {
    public static final String ROOT_DIR_KEY = "root-dir";

    @Override
    public FileStorage create(Map<String, String> params) throws FileStorageCreationException {
        if (!params.containsKey(ROOT_DIR_KEY)) {
            throw new FileStorageCreationException("Missing parameter " +  ROOT_DIR_KEY);
        }
        Path rootDir = Path.of(params.get(ROOT_DIR_KEY)).normalize();
        try {
            Files.createDirectories(rootDir);
        } catch (IOException e) {
            throw new FileStorageCreationException("Failed to create root directory", e);
        }
        return new LocalFileStorage(rootDir);
    }
}
