package com.yairz.elfalyze.elf.models;

public enum ElfBitness {
    INVALID((byte) 0),
    BITNESS_32((byte) 1),
    BITNESS_64((byte) 2),
    ;
    private final byte bitnessCode;

    ElfBitness(byte bitnessCode) {
        this.bitnessCode = bitnessCode;
    }

    public static ElfBitness fromBitnessCode(byte bitnessCode) {
        for (ElfBitness bit : ElfBitness.values()) {
            if (bit.getBitnessCode() == bitnessCode) {
                return bit;
            }
        }
        throw new IllegalArgumentException("Unknown ElfBitness code: " + bitnessCode);
    }

    public byte getBitnessCode() {
        return bitnessCode;
    }
}
