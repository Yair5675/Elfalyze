package com.yairz.elfalyze.elf.types;

import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf32_Half and Elf64_Half data types specified by the ELF format.
 * Represents an unsigned medium integer.
 *
 * @param half The **unsigned** half. Both Elf32_Half and Elf64_Half are 2 bytes, so a short is good for
 *             both.
 */
public record ElfHalf(short half) implements Comparable<ElfHalf> {
    @Override
    public int compareTo(@NonNull ElfHalf other) {
        return Short.compareUnsigned(half, other.half);
    }
}
