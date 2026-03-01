package com.yairz.elfalyze.data.files.creators;

import com.yairz.elfalyze.data.files.FileStorage;
import com.yairz.elfalyze.data.files.exceptions.FileStorageCreationException;

import java.util.Map;

public interface FileStorageCreator {
    FileStorage create(Map<String, String> params) throws FileStorageCreationException;
}
