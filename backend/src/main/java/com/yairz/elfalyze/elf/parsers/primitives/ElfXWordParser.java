package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfXWord;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfXWordParser implements Elf64PrimitiveParser<ElfXWord> {
    @Override
    public ElfXWord parse64(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfXWord.XWORD_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf X-Word requires %d bytes in 64-bit format, only %d were given",
                    ElfXWord.XWORD_SIZE_64, remaining
            ));
        }

        return new ElfXWord(rawBytes.getLong());
    }
}
