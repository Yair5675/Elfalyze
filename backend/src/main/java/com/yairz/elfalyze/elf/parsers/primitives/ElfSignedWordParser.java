package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfSignedWord;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfSignedWordParser implements Elf32PrimitiveParser<ElfSignedWord>, Elf64PrimitiveParser<ElfSignedWord> {
    @Override
    public ElfSignedWord parse32(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfSignedWord.SIGNED_WORD_SIZE_32) {
            throw new ElfParsingException(String.format(
                    "Elf Signed Word requires %d bytes in 32-bit format, only %d were given",
                    ElfSignedWord.SIGNED_WORD_SIZE_32, remaining
            ));
        }

        return new ElfSignedWord(rawBytes.getInt());
    }

    @Override
    public ElfSignedWord parse64(ByteBuffer rawBytes) {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfSignedWord.SIGNED_WORD_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf Signed Word requires %d bytes in 64-bit format, only %d were given",
                    ElfSignedWord.SIGNED_WORD_SIZE_64, remaining
            ));
        }

        return new ElfSignedWord(rawBytes.getInt());
    }
}
