package com.yairz.elfalyze.elf;

import com.yairz.elfalyze.util.Sha256;
import com.yairz.elfalyze.exceptions.elf.ElfServiceException;
import com.yairz.elfalyze.storage.FileId;
import com.yairz.elfalyze.storage.FileStorage;
import com.yairz.elfalyze.exceptions.storage.FileStorageException;
import jakarta.annotation.Nullable;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Optional;

@Service
public class ElfService {
    private static final String SHA_256 = "SHA-256";

    private final ElfRepository elfRepository;
    private final FileStorage fileStorage;

    public ElfService(ElfRepository elfRepository, FileStorage fileStorage) {
        this.elfRepository = elfRepository;
        this.fileStorage = fileStorage;
    }

    public Optional<Elf> getElfById(@Nullable Long elfId) {
        if (elfId == null) {
            return Optional.empty();
        }

        return elfRepository.findById(elfId);
    }

    public void deleteElfById(@NonNull Long elfId) {
        elfRepository.deleteById(elfId);
    }

    public Elf uploadElf(String elfName, long elfSize, InputStream elfContent) throws ElfServiceException {
        FileUploadResult uploadResult = uploadElfContentToFileStorage(elfContent);
        Elf elf = new Elf(elfName, uploadResult.fileId, elfSize, uploadResult.fileSha256, Instant.now());
        return elfRepository.save(elf);
    }

    private FileUploadResult uploadElfContentToFileStorage(InputStream elfContent) throws ElfServiceException {
        try (DigestInputStream dis = new DigestInputStream(elfContent, null)) {
            final MessageDigest digest = MessageDigest.getInstance(SHA_256);
            dis.setMessageDigest(digest);

            // TODO Save to temporary file, perform validation, then upload to storage
            FileId fileId = fileStorage.store(dis);
            Sha256 sha256 = new Sha256(digest.digest());
            return new FileUploadResult(fileId, sha256);
        } catch (IOException | NoSuchAlgorithmException | FileStorageException e) {
            throw new ElfServiceException(e);
        }

    }

    private record FileUploadResult(FileId fileId, Sha256 fileSha256) {
    }
}
