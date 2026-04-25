package com.yairz.elfalyze.elf.models.sections;

import com.yairz.elfalyze.elf.types.ElfHalf;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.HexFormat;
import java.util.List;
import java.util.function.Function;

public class ElfSectionIndex {
    /**
     * Undefined, missing, irrelevant, or otherwise meaningless section reference.
     * For example, a symbol “defined” relative to this section number is an undefined symbol.
     */
    public static final ElfHalf UNDEFINED_SECTION_INDEX = new ElfHalf((short) 0);

    /**
     * This value specifies the lower bound of the range of reserved indexes. The system reserves
     * indices between this value and {@link ElfSectionIndex#LAST_RESERVED_SECTION_INDEX} (inclusive); the values
     * do not reference the section header table. The section header table does not contain entries for the reserved
     * indexes.
     */
    public static final ElfHalf FIRST_RESERVED_SECTION_INDEX = new ElfHalf((short) 0xff00);

    /**
     * This value specifies the upper bound of the range of reserved indexes. The system reserves
     * indices between {@link ElfSectionIndex#FIRST_RESERVED_SECTION_INDEX} and this value (inclusive); the values
     * do not reference the section header table. The section header table does not contain entries for the reserved
     * indexes.
     */
    public static final ElfHalf LAST_RESERVED_SECTION_INDEX = new ElfHalf((short) 0xffff);

    /**
     * Values between this value and {@link ElfSectionIndex#LAST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX}
     * (inclusively) are reserved for processor-specific semantics.
     */
    public static final ElfHalf FIRST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX = new ElfHalf((short) 0xff00);

    /**
     * Values between {@link ElfSectionIndex#FIRST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX} and this value
     * (inclusively) are reserved for processor-specific semantics.
     */
    public static final ElfHalf LAST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX = new ElfHalf((short) 0xff1f);

    /**
     * Values between this value and {@link ElfSectionIndex#LAST_OS_SPECIFIC_RESERVED_SECTION_INDEX} (inclusively)
     * are reserved for operating system-specific semantics.
     */
    public static final ElfHalf FIRST_OS_SPECIFIC_RESERVED_SECTION_INDEX = new ElfHalf((short) 0xff20);

    /**
     * Values between {@link ElfSectionIndex#FIRST_OS_SPECIFIC_RESERVED_SECTION_INDEX} and this value (inclusively)
     * are reserved for operating system-specific semantics.
     */
    public static final ElfHalf LAST_OS_SPECIFIC_RESERVED_SECTION_INDEX = new ElfHalf((short) 0xff3f);

    /**
     * This value specifies absolute values for the corresponding reference. For example, symbols defined relative to
     * this section number have absolute values and are not affected by relocation.
     */
    public static final ElfHalf ABSOLUTE_SECTION_INDEX = new ElfHalf((short) 0xfff1);

    /**
     * Symbols defined relative to this section are common symbols, such as FORTRAN COMMON or unallocated C external
     * variables.
     */
    public static final ElfHalf COMMON_SYMBOLS_SECTION_INDEX = new ElfHalf((short) 0xfff2);

    /**
     * This value is an escape value. It indicates that the actual section header index is too large to fit in the
     * containing field and is to be found in another location (specific to the structure where it appears).
     */
    public static final ElfHalf ESCAPE_SECTION_INDEX = new ElfHalf((short) 0xffff);

    private final ElfHalf rawIndex;

    @VisibleForTesting
    ElfSectionIndex(short rawIndex) {
        this(new ElfHalf(rawIndex));
    }

    @VisibleForTesting
    ElfSectionIndex(ElfHalf rawIndex) {
        this.rawIndex = rawIndex;
    }

    public static ElfSectionIndex fromRawSectionIndex(ElfHalf rawIndex) {
        return new ElfSectionIndex(rawIndex);
    }

    private static void validateRawSectionIndex(ElfHalf rawIndex) {
        if (!isReservedSectionIndex(rawIndex)) return;
        List<Function<ElfHalf, Boolean>> knownSpecialIndicesCheckers = List.of(
                ElfSectionIndex::isProcessorSpecificSectionIndex,
                ElfSectionIndex::isOsSpecificSectionIndex,
                ElfSectionIndex::isAbsoluteSectionIndex,
                ElfSectionIndex::isEscapeSectionIndex,
                ElfSectionIndex::isCommonSymbolsSectionIndex
        );
        for (Function<ElfHalf, Boolean> knownSpecialIndicesChecker : knownSpecialIndicesCheckers) {
            if (knownSpecialIndicesChecker.apply(rawIndex)) return;
        }

        final String invalidShortHex = HexFormat.of().withLowerCase().toHexDigits(rawIndex.half());
        throw new IllegalArgumentException("Invalid raw section index: 0x" + invalidShortHex);
    }

    private static boolean isProcessorSpecificSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.compareTo(FIRST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX) >= 0 &&
                sectionIndex.compareTo(LAST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX) <= 0;
    }

    private static boolean isOsSpecificSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.compareTo(FIRST_OS_SPECIFIC_RESERVED_SECTION_INDEX) >= 0 &&
                sectionIndex.compareTo(LAST_OS_SPECIFIC_RESERVED_SECTION_INDEX) <= 0;
    }

    private static boolean isAbsoluteSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.equals(ABSOLUTE_SECTION_INDEX);
    }

    private static boolean isEscapeSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.equals(ESCAPE_SECTION_INDEX);
    }

    private static boolean isCommonSymbolsSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.equals(COMMON_SYMBOLS_SECTION_INDEX);
    }

    private static boolean isUndefinedSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.equals(UNDEFINED_SECTION_INDEX);
    }

    private static boolean isReservedSectionIndex(ElfHalf sectionIndex) {
        return sectionIndex.compareTo(FIRST_RESERVED_SECTION_INDEX) >= 0 &&
                sectionIndex.compareTo(LAST_RESERVED_SECTION_INDEX) <= 0;
    }

    public ElfHalf getRawIndex() {
        return rawIndex;
    }

    public boolean isReserved() {
        return isReservedSectionIndex(rawIndex);
    }

    public boolean isProcessorSpecific() {
        return isProcessorSpecificSectionIndex(rawIndex);
    }

    public boolean isOsSpecific() {
        return isOsSpecificSectionIndex(rawIndex);
    }

    public boolean isUndefined() {
        return isUndefinedSectionIndex(rawIndex);
    }

    public boolean isCommonSymbols() {
        return isCommonSymbolsSectionIndex(rawIndex);
    }

    public boolean isAbsolute() {
        return isAbsoluteSectionIndex(rawIndex);
    }

    public boolean isEscape() {
        return isEscapeSectionIndex(rawIndex);
    }

    public boolean isReserved(ElfHalf sectionIndex) {
        return isReservedSectionIndex(sectionIndex);
    }
}
