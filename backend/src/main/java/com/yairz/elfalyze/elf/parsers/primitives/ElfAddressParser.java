package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfAddress;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfAddressParser implements Elf32PrimitiveParser<ElfAddress>, Elf64PrimitiveParser<ElfAddress> {
    @Override
    public ElfAddress parse32(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfAddress.ADDRESS_SIZE_32) {
            throw new ElfParsingException(String.format(
                    "Elf Address requires %d bytes in 32-bit format, only %d were given",
                    ElfAddress.ADDRESS_SIZE_32, remaining
            ));
        }

        return new ElfAddress(Integer.toUnsignedLong(rawBytes.getInt()));
    }

    @Override
    public ElfAddress parse64(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfAddress.ADDRESS_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf Address requires %d bytes in 64-bit format, only %d were given",
                    ElfAddress.ADDRESS_SIZE_64, remaining
            ));
        }

        return new ElfAddress(rawBytes.getLong());
    }
}
