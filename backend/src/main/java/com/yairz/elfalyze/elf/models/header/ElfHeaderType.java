package com.yairz.elfalyze.elf.models.header;

import com.yairz.elfalyze.elf.types.ElfHalf;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.HexFormat;
import java.util.Map;

public final class ElfHeaderType {
    public static final ElfHalf FIRST_OS_SPECIFIC_VALUE = new ElfHalf((short) 0xfe00);
    public static final ElfHalf LAST_OS_SPECIFIC_VALUE = new ElfHalf((short) 0xfeff);

    public static final ElfHalf FIRST_PROCESSOR_SPECIFIC_VALUE = new ElfHalf((short) 0xff00);
    public static final ElfHalf LAST_PROCESSOR_SPECIFIC_VALUE = new ElfHalf((short) 0xffff);

    private static final Map<Short, String> HEADER_TYPE_VALUES_TO_TYPE_NAME = Map.of(
            (short) 0, "No File Type",
            (short) 1, "Relocatable File",
            (short) 2, "Executable File",
            (short) 3, "Shared Object File",
            (short) 4, "Core File"
    );

    private final ElfHalf rawType;

    @VisibleForTesting
    ElfHeaderType(ElfHalf rawType) {
        this.rawType = rawType;
    }

    public static ElfHeaderType fromHeaderTypeValue(ElfHalf headerTypeValue) {
        validateHeaderTypeValue(headerTypeValue);
        return new ElfHeaderType(headerTypeValue);
    }

    private static void validateHeaderTypeValue(ElfHalf headerTypeValue) {
        if (HEADER_TYPE_VALUES_TO_TYPE_NAME.containsKey(headerTypeValue.half())) return;
        if (isOsSpecificHeaderType(headerTypeValue)) return;
        if (isProcessorSpecificHeaderType(headerTypeValue)) return;
        final String invalidShortHex = HexFormat.of().withLowerCase().toHexDigits(headerTypeValue.half());
        throw new IllegalArgumentException("Invalid header type value: 0x" + invalidShortHex);
    }

    private static boolean isOsSpecificHeaderType(ElfHalf headerTypeValue) {
        return FIRST_OS_SPECIFIC_VALUE.compareTo(headerTypeValue) <= 0 &&
                LAST_OS_SPECIFIC_VALUE.compareTo(headerTypeValue) >= 0;
    }

    private static boolean isProcessorSpecificHeaderType(ElfHalf headerTypeValue) {
        return FIRST_PROCESSOR_SPECIFIC_VALUE.compareTo(headerTypeValue) <= 0 &&
                LAST_PROCESSOR_SPECIFIC_VALUE.compareTo(headerTypeValue) >= 0;
    }

    public ElfHalf getRawType() {
        return rawType;
    }

    public boolean isOsSpecific() {
        return isOsSpecificHeaderType(rawType);
    }

    public boolean isProcessorSpecific() {
        return isProcessorSpecificHeaderType(rawType);
    }

    public String getHeaderTypeName() {
        if (isOsSpecific()) {
            return "OS-Specific";
        }
        if (isProcessorSpecific()) {
            return "Processor-Specific";
        }
        return HEADER_TYPE_VALUES_TO_TYPE_NAME.get(rawType.half());
    }

    @Override
    public String toString() {
        final String name = getHeaderTypeName();
        final String hexType = HexFormat.of().withLowerCase().toHexDigits(rawType.half());
        return String.format("%s (0x%s)", name, hexType);
    }
}
