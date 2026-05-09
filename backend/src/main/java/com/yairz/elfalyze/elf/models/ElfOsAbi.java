package com.yairz.elfalyze.elf.models;

import org.jetbrains.annotations.VisibleForTesting;

import java.util.HashMap;
import java.util.Map;

public final class ElfOsAbi {
    private static final byte FIRST_ARCHITECTURE_SPECIFIC_CODE = 64;

    private static final Map<Byte, String> ABI_CODES_TO_NAMES = new HashMap<>();

    static {
        ABI_CODES_TO_NAMES.put((byte) 0, "None");
        ABI_CODES_TO_NAMES.put((byte) 1, "Hewlett-Packard HP-UX");
        ABI_CODES_TO_NAMES.put((byte) 2, "NetBSD");
        ABI_CODES_TO_NAMES.put((byte) 3, "GNU");
        ABI_CODES_TO_NAMES.put((byte) 6, "Sun Solaris");
        ABI_CODES_TO_NAMES.put((byte) 7, "AIX");
        ABI_CODES_TO_NAMES.put((byte) 8, "IRIX");
        ABI_CODES_TO_NAMES.put((byte) 9, "FreeBSD");
        ABI_CODES_TO_NAMES.put((byte) 10, "Compaq TRU64 UNIX");
        ABI_CODES_TO_NAMES.put((byte) 11, "Novell Modesto");
        ABI_CODES_TO_NAMES.put((byte) 12, "Open BSD");
        ABI_CODES_TO_NAMES.put((byte) 13, "Open VMS");
        ABI_CODES_TO_NAMES.put((byte) 14, "Hewlett-Packard Non-Stop Kernel");
        ABI_CODES_TO_NAMES.put((byte) 15, "Amiga Research OS");
        ABI_CODES_TO_NAMES.put((byte) 16, "The FenixOS highly scalable multi-core OS");
        ABI_CODES_TO_NAMES.put((byte) 17, "Nuxi CloudABI");
        ABI_CODES_TO_NAMES.put((byte) 18, "Stratus Technologies OpenVOS");
    }

    private final int osAbiCode;

    @VisibleForTesting
    ElfOsAbi(int osAbiCode) {
        this.osAbiCode = osAbiCode;
    }

    public static ElfOsAbi fromOsAbiCode(byte osAbiCode) {
        validateOsAbiCode(osAbiCode);
        return new ElfOsAbi(Byte.toUnsignedInt(osAbiCode));
    }

    private static void validateOsAbiCode(byte osAbiCode) {
        final int unsignedOsAbiCode = Byte.toUnsignedInt(osAbiCode);
        if (unsignedOsAbiCode < FIRST_ARCHITECTURE_SPECIFIC_CODE &&
                !ABI_CODES_TO_NAMES.containsKey(osAbiCode)) {
            throw new IllegalArgumentException("Invalid OS-ABI code: " + unsignedOsAbiCode);
        }
    }

    public int getOsAbiCode() {
        return osAbiCode;
    }

    public String getOsAbiName() {
        return ABI_CODES_TO_NAMES.getOrDefault((byte) osAbiCode, "Architecture Specific");
    }

    @Override
    public String toString() {
        final String name = getOsAbiName();
        return String.format("%s (%d)", name, osAbiCode);
    }
}
