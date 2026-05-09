package com.yairz.elfalyze.elf.types;

import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf32_Half and Elf64_Half data types specified by the ELF format.
 * Represents an unsigned medium integer.
 *
 * @param half The **unsigned** half. Both Elf32_Half and Elf64_Half are 2 bytes, so a short is good for
 *             both.
 */
public record ElfHalf(short half) implements Comparable<ElfHalf>, Elf32Primitive, Elf64Primitive {
    public static final int HALF_SIZE_32 = 2;
    public static final int HALF_SIZE_64 = 2;

    @Override
    public int compareTo(@NonNull ElfHalf other) {
        return Short.compareUnsigned(half, other.half);
    }
}
