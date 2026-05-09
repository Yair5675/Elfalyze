package com.yairz.elfalyze.elf.types;

/**
 * Sealed interface marking all 64-bit data types the ELF spec defines.
 */
public sealed interface Elf64Primitive extends ElfPrimitive
        permits ElfAddress, ElfOffset, ElfHalf, ElfWord, ElfSignedWord, ElfXWord, ElfSignedXWord {
}
