package com.yairz.elfalyze.elf.types;

import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf32_Word and Elf64_Word data types specified by the ELF format.
 * Represents an unsigned integer.
 *
 * @param word The **unsigned** word. Both Elf32_Word and Elf64_Word are 4 bytes, so an int is good for
 *             both.
 */
public record ElfWord(int word) implements Comparable<ElfWord>, Elf32Primitive, Elf64Primitive {
    public static final int WORD_SIZE_32 = 4;
    public static final int WORD_SIZE_64 = 4;

    @Override
    public int compareTo(@NonNull ElfWord other) {
        return Integer.compareUnsigned(word, other.word);
    }
}
