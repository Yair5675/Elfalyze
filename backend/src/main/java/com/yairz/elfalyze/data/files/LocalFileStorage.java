package com.yairz.elfalyze.data.files;

import com.yairz.elfalyze.data.files.exceptions.FileStorageException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class LocalFileStorage implements FileStorage {
    private final Path root;

    public LocalFileStorage(Path root) {
        this.root = root.toAbsolutePath().normalize();
    }

    @Override
    public FileId store(InputStream content) throws FileStorageException {
        String filename = UUID.randomUUID().toString();
        Path filepath = root.resolve(filename);
        try (OutputStream out = Files.newOutputStream(filepath, StandardOpenOption.CREATE_NEW)) {
            content.transferTo(out);
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
        return new FileId(filename);
    }

    @Override
    public InputStream read(FileId fileId) throws FileStorageException {
        Path filepath = validateAndResolvePath(fileId);
        try {
            return Files.newInputStream(filepath);
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    @Override
    public List<FileId> listFiles() throws FileStorageException {
        try (Stream<Path> files = Files.list(this.root)) {
            return files
                    .filter(Files::isRegularFile)
                    .map(path -> new FileId(path.getFileName().toString()))
                    .toList();
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    @Override
    public void delete(FileId fileId) throws FileStorageException {
        Path filepath = validateAndResolvePath(fileId);
        try {
            Files.delete(filepath);
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    private Path validateAndResolvePath(FileId fileId) throws FileStorageException {
        Path filePath = root.resolve(fileId.rawId()).normalize();
        if (!filePath.startsWith(root)) {
            throw new FileStorageException("Given ID does not belong to the root directory " + root);
        }
        if (!Files.exists(filePath)) {
            throw new FileStorageException("Given ID does not exist " + filePath);
        }
        return filePath;
    }
}
