package com.yairz.elfalyze.elf;


import com.yairz.elfalyze.converters.FileIdConverter;
import com.yairz.elfalyze.converters.Sha256Converter;
import com.yairz.elfalyze.util.Sha256;
import com.yairz.elfalyze.storage.FileId;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class Elf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = FileIdConverter.class)
    private FileId fileId;

    private long size;

    @Column(nullable = false, unique = true)
    @Convert(converter = Sha256Converter.class)
    private Sha256 sha256;

    @Column(nullable = false)
    private Instant uploadTime;

    protected Elf() {}

    public Elf(String name, FileId fileId, long size, Sha256 sha256, Instant uploadTime) {
        this.name = name;
        this.fileId = fileId;
        this.size = size;
        this.sha256 = sha256;
        this.uploadTime = uploadTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public FileId getFileId() {
        return fileId;
    }

    public long getSize() {
        return size;
    }

    public Sha256 getSha256() {
        return sha256;
    }

    public Instant getUploadTime() {
        return uploadTime;
    }

    @Override
    public String toString() {
        return "Elf{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fileId=" + fileId +
                ", size=" + size +
                ", sha256=" + sha256 +
                ", uploadTime=" + uploadTime +
                '}';
    }
}
