package com.yairz.elfalyze.elf.types;


import org.jspecify.annotations.NonNull;

/**
 * Abstraction over the Elf32_Addr and Elf64_Addr data types specified by the ELF format.
 * Represents an unsigned program address.
 * <br>
 * The value held, whether 4 bytes or 8, is always unsigned.
 *
 * @param address The **unsigned** address. Elf32_Addr is 4 bytes while Elf64_Addr is 8, so we always
 *                pick the larger one of the two.
 */
public record ElfAddress(long address) implements Comparable<ElfAddress> {
    public static ElfAddress fromInt(int raw32Address) {
        return new ElfAddress(Integer.toUnsignedLong(raw32Address));
    }

    @Override
    public int compareTo(@NonNull ElfAddress other) {
        return Long.compareUnsigned(this.address, other.address);
    }
}
