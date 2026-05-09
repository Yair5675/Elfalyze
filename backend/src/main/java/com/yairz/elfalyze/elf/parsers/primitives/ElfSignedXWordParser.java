package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.ElfSignedXWord;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ElfSignedXWordParser implements Elf64PrimitiveParser<ElfSignedXWord> {
    @Override
    public ElfSignedXWord parse64(ByteBuffer rawBytes) throws ElfParsingException {
        final int remaining = rawBytes.remaining();
        if (remaining < ElfSignedXWord.SIGNED_XWORD_SIZE_64) {
            throw new ElfParsingException(String.format(
                    "Elf Signed X-Word requires %d bytes in 64-bit format, only %d were given",
                    ElfSignedXWord.SIGNED_XWORD_SIZE_64, remaining
            ));
        }

        return new ElfSignedXWord(rawBytes.getLong());
    }
}
