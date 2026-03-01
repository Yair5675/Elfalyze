package com.yairz.elfalyze.storage.creators;

import com.yairz.elfalyze.storage.FileStorage;
import com.yairz.elfalyze.exceptions.storage.FileStorageCreationException;

import java.util.Map;

public interface FileStorageCreator {
    FileStorage create(Map<String, String> params) throws FileStorageCreationException;
}
