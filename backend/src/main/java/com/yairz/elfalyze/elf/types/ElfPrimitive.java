package com.yairz.elfalyze.elf.types;

/**
 * Sealed interface for forcing the type system of java to use types defined by the ELF spec.
 */
public sealed interface ElfPrimitive permits Elf32Primitive, Elf64Primitive {
}
