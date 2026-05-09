package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfOffset;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfOffsetParser implements Elf32PrimitiveParser<ElfOffset>, Elf64PrimitiveParser<ElfOffset> {
    @Override
    public ElfOffset parse32(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfOffset.OFFSET_SIZE_32) {
            throw new ElfParsingException(String.format(
                    "Elf Offset requires %d bytes in 32-bit format, only %d were given",
                    ElfOffset.OFFSET_SIZE_32, remaining
            ));
        }

        return new ElfOffset(Integer.toUnsignedLong(rawBytes.getInt()));
    }

    @Override
    public ElfOffset parse64(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfOffset.OFFSET_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf Offset requires %d bytes in 64-bit format, only %d were given",
                    ElfOffset.OFFSET_SIZE_64, remaining
            ));
        }

        return new ElfOffset(rawBytes.getLong());
    }
}
