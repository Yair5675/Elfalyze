package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfWord;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfWordParser implements Elf32PrimitiveParser<ElfWord>, Elf64PrimitiveParser<ElfWord> {
    @Override
    public ElfWord parse32(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfWord.WORD_SIZE_32) {
            throw new ElfParsingException(String.format(
                    "Elf Word requires %d bytes in 32-bit format, only %d were given",
                    ElfWord.WORD_SIZE_32, remaining
            ));
        }

        return new ElfWord(rawBytes.getInt());
    }

    @Override
    public ElfWord parse64(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfWord.WORD_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf Word requires %d bytes in 64-bit format, only %d were given",
                    ElfWord.WORD_SIZE_64, remaining
            ));
        }

        return new ElfWord(rawBytes.getInt());
    }
}
