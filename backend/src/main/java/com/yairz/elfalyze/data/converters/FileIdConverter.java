package com.yairz.elfalyze.data.converters;

import com.yairz.elfalyze.data.files.FileId;
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
