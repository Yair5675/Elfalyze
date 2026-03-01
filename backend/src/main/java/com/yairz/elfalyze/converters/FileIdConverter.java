package com.yairz.elfalyze.converters;

import com.yairz.elfalyze.storage.FileId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FileIdConverter implements AttributeConverter<FileId, String> {
    @Override
    public String convertToDatabaseColumn(FileId fileId) {
        return fileId == null ? null : fileId.rawId();
    }

    @Override
    public FileId convertToEntityAttribute(String rawId) {
        return rawId == null ? null : new FileId(rawId);
    }
}
