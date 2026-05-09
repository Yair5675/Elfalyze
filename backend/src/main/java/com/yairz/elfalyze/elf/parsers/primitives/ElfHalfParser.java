package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfHalf;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfHalfParser implements Elf32PrimitiveParser<ElfHalf>, Elf64PrimitiveParser<ElfHalf> {
    @Override
    public ElfHalf parse32(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfHalf.HALF_SIZE_32) {
            throw new ElfParsingException(String.format(
                    "Elf Half requires %d bytes in 32-bit format, only %d were given",
                    ElfHalf.HALF_SIZE_32, remaining
            ));
        }

        return new ElfHalf(rawBytes.getShort());
    }

    @Override
    public ElfHalf parse64(ByteBuffer rawBytes) {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfHalf.HALF_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf Half requires %d bytes in 64-bit format, only %d were given",
                    ElfHalf.HALF_SIZE_64, remaining
            ));
        }

        return new ElfHalf(rawBytes.getShort());
    }
}
