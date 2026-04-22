package com.yairz.elfalyze.elf.types;


import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf32_Sword and Elf64_Sword data types specified by the ELF format.
 * Represents a signed integer.
 *
 * @param signedWord The **signed** word. Both Elf32_Sword and Elf64_Sword are 4 bytes, so an int is
 *                   good for both.
 */
public record ElfSignedWord(int signedWord) implements Comparable<ElfSignedWord> {
    @Override
    public int compareTo(@NonNull ElfSignedWord other) {
        return Integer.compare(signedWord, other.signedWord);
    }
}
