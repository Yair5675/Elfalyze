package com.yairz.elfalyze.elf.types;

import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf64_Sxword data type specified by the ELF format.
 * Represents a signed long integer.
 *
 * @param signedXword The **unsigned** long integer. Its size is 8 bytes.
 */
public record ElfSignedXWord(long signedXword) implements Comparable<ElfSignedXWord> {
    @Override
    public int compareTo(@NonNull ElfSignedXWord other) {
        return Long.compare(signedXword, other.signedXword);
    }
}
