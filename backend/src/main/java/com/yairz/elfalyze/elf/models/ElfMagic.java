package com.yairz.elfalyze.elf.models;

public final class ElfMagic {

    public static final int MAGIC_SIZE = 4;
    public static final byte VALID_MAGIC_BYTE_1 = 0x7f;
    public static final byte VALID_MAGIC_BYTE_2 = 'E';
    public static final byte VALID_MAGIC_BYTE_3 = 'L';
    public static final byte VALID_MAGIC_BYTE_4 = 'F';
    private final byte mag1;
    private final byte mag2;
    private final byte mag3;
    private final byte mag4;

    // Visible For Testing
    ElfMagic(byte mag1, byte mag2, byte mag3, byte mag4) {
        this.mag1 = mag1;
        this.mag2 = mag2;
        this.mag3 = mag3;
        this.mag4 = mag4;
    }

    /**
     * Creates an {@link ElfMagic} object out of the given raw bytes, performing validation on them.
     *
     * @param rawMagic Raw bytes which hold the magic bytes of an ELF file. Must be of size {@link ElfMagic#MAGIC_SIZE}
     *                 and hold the values of {@link ElfMagic#VALID_MAGIC_BYTE_1} to {@link ElfMagic#VALID_MAGIC_BYTE_4}
     *                 otherwise an {@link IllegalArgumentException} will be thrown.
     * @return A wrapper around the magic bytes.
     * @throws IllegalArgumentException If the given raw bytes do not adhere to the ELF format specification as detailed
     *                                  above.
     */
    public static ElfMagic fromBytes(byte[] rawMagic) {
        validateRawMagic(rawMagic);
        return new ElfMagic(rawMagic[0], rawMagic[1], rawMagic[2], rawMagic[3]);
    }

    private static void validateRawMagic(byte[] rawMagic) {
        if (rawMagic.length != MAGIC_SIZE) {
            throw new IllegalArgumentException("Magic size must be " + MAGIC_SIZE);
        }
        if (rawMagic[0] != VALID_MAGIC_BYTE_1) {
            throw new IllegalArgumentException("Invalid magic byte #1: " + rawMagic[0]);
        }
        if (rawMagic[1] != VALID_MAGIC_BYTE_2) {
            throw new IllegalArgumentException("Invalid magic byte #2: " + rawMagic[1]);
        }
        if (rawMagic[2] != VALID_MAGIC_BYTE_3) {
            throw new IllegalArgumentException("Invalid magic byte #3: " + rawMagic[2]);
        }
        if (rawMagic[3] != VALID_MAGIC_BYTE_4) {
            throw new IllegalArgumentException("Invalid magic byte #4: " + rawMagic[3]);
        }
    }

    public byte[] getRawMagic() {
        return new byte[]{mag1, mag2, mag3, mag4};
    }
}
