package com.yairz.elfalyze.elf.types;

import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf64_Xword data type specified by the ELF format.
 * Represents an unsigned long integer.
 *
 * @param xword The **unsigned** long integer. Its size is 8 bytes.
 */
public record ElfXWord(long xword) implements Comparable<ElfXWord> {
    @Override
    public int compareTo(@NonNull ElfXWord other) {
        return Long.compareUnsigned(xword, other.xword);
    }
}
