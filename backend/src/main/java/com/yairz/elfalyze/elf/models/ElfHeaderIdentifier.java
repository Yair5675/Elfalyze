package com.yairz.elfalyze.elf.models;

public record ElfHeaderIdentifier(ElfMagic magic, ElfBitness bitness, ElfEndianness endianness,
                                  ElfHeaderVersion version, ElfOsAbi osAbi, int osAbiVersion) {
}
