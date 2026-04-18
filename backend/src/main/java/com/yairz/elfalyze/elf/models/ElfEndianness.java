package com.yairz.elfalyze.elf.models;

public enum ElfEndianness {
    INVALID((byte) 0),
    LSB((byte) 1),
    MSB((byte) 2),
    ;
    private final byte endiannessCode;

    ElfEndianness(byte endiannessCode) {
        this.endiannessCode = endiannessCode;
    }

    public static ElfEndianness fromEndiannessCode(byte endiannessCode) {
        for (ElfEndianness endianness : ElfEndianness.values()) {
            if (endianness.endiannessCode == endiannessCode) {
                return endianness;
            }
        }
        throw new IllegalArgumentException("Invalid endianness code: " + endiannessCode);
    }

    public byte getEndiannessCode() {
        return endiannessCode;
    }
}
