package com.yairz.elfalyze.elf.types;


/**
 * Abstraction over the Elf32_Off and Elf64_Off data types specified by the ELF format.
 * Represents an unsigned file offset.
 * <br>
 * The value held, whether 4 bytes or 8, is always unsigned.
 *
 * @param offset The **unsigned** offset. Elf32_Off is 4 bytes while Elf64_Off is 8, so we always
 *               pick the larger one of the two.
 */
public record ElfOffset(long offset) implements Comparable<ElfOffset> {
    public static ElfOffset fromInt(int raw32Offset) {
        return new ElfOffset(Integer.toUnsignedLong(raw32Offset));
    }

    @Override
    public int compareTo(ElfOffset other) {
        return Long.compareUnsigned(offset, other.offset);
    }
}
