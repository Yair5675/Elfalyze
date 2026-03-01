package com.yairz.elfalyze.storage;

import com.yairz.elfalyze.storage.creators.FileStorageCreator;
import com.yairz.elfalyze.storage.creators.LocalFileStorageCreator;
import com.yairz.elfalyze.exceptions.storage.FileStorageCreationException;

import java.util.Map;

public final class FileStorageFactory {
    private final Map<String, FileStorageCreator> storageCreators;

    public FileStorageFactory() {
        storageCreators = Map.of(
                "local", new LocalFileStorageCreator()
        );
    }

    public FileStorage create(String storageType, Map<String, String> storageParams) throws FileStorageCreationException {
        if (!storageCreators.containsKey(storageType)) {
            throw new FileStorageCreationException("Unknown storage type: " + storageType);
        }
        FileStorageCreator fileStorageCreator = storageCreators.get(storageType);
        return fileStorageCreator.create(storageParams);
    }
}
