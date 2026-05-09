package com.yairz.elfalyze.elf.types;

/**
 * Sealed interface marking all 32-bit data types the ELF spec defines.
 */
public sealed interface Elf32Primitive extends ElfPrimitive
        permits ElfAddress, ElfOffset, ElfHalf, ElfWord, ElfSignedWord {
}
