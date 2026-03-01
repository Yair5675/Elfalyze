package com.yairz.elfalyze.data.converters;

import com.yairz.elfalyze.data.elf.Sha256;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class Sha256Converter implements AttributeConverter<Sha256, byte[]> {
    @Override
    public byte[] convertToDatabaseColumn(Sha256 sha256) {
        return sha256 == null ? null : sha256.bytes();
    }

    @Override
    public Sha256 convertToEntityAttribute(byte[] bytes) {
        return bytes == null ? null : new Sha256(bytes);
    }
}
