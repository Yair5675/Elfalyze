package com.yairz.elfalyze.elf.models.sections

import com.yairz.elfalyze.elf.types.ElfHalf
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream
import kotlin.streams.asStream

class ElfSectionIndexTest {

    @Test
    fun `getRawIndex returns the correct ElfHalf instance`() {
        val rawValue = ElfHalf(0x10.toShort())
        val sectionIndex = ElfSectionIndex(rawValue)

        assertEquals(rawValue, sectionIndex.rawIndex)
    }

    @ParameterizedTest
    @MethodSource("regularIndicesProvider")
    fun `regular index returns false on any special index predicate`(value: Short) {
        val sectionIndex = ElfSectionIndex(value)
        assertFalse(sectionIndex.isReserved)
        assertFalse(sectionIndex.isUndefined)
        assertFalse(sectionIndex.isAbsolute)
        assertFalse(sectionIndex.isCommonSymbols)
        assertFalse(sectionIndex.isEscape)
        assertFalse(sectionIndex.isOsSpecific)
        assertFalse(sectionIndex.isProcessorSpecific)
    }

    @ParameterizedTest
    @MethodSource("reservedIndicesProvider")
    fun `isReserved returns true for values in the reserved range`(value: Short) {
        assertTrue(ElfSectionIndex(value).isReserved)
    }

    @ParameterizedTest
    @MethodSource("processorSpecificProvider")
    fun `isProcessorSpecific returns true for indices in range 0xFF00 to 0xFF1F`(value: Short) {
        val sectionIndex = ElfSectionIndex(value)
        assertTrue(sectionIndex.isProcessorSpecific)
        assertTrue(sectionIndex.isReserved)
    }

    @ParameterizedTest
    @MethodSource("osSpecificProvider")
    fun `isOsSpecific returns true for indices in range 0xFF20 to 0xFF3F`(value: Short) {
        val sectionIndex = ElfSectionIndex(value)
        assertTrue(sectionIndex.isOsSpecific)
        assertTrue(sectionIndex.isReserved)
    }

    @Test
    fun `isAbsolute returns true for its raw value`() {
        assertTrue(ElfSectionIndex(ElfSectionIndex.ABSOLUTE_SECTION_INDEX.half).isAbsolute)
    }

    @Test
    fun `isCommonSymbols returns true for its raw value`() {
        assertTrue(ElfSectionIndex(ElfSectionIndex.COMMON_SYMBOLS_SECTION_INDEX.half).isCommonSymbols)
    }

    @Test
    fun `isEscape returns true for its raw value`() {
        assertTrue(ElfSectionIndex(ElfSectionIndex.ESCAPE_SECTION_INDEX.half).isEscape)
    }

    @Test
    fun `isUndefined returns true for its raw value`() {
        assertTrue(ElfSectionIndex(ElfSectionIndex.UNDEFINED_SECTION_INDEX.half).isUndefined)
    }

    @Test
    fun `isReserved with argument works independently of instance state`() {
        val instance = ElfSectionIndex(0.toShort())

        assertTrue(instance.isReserved(ElfHalf(0xff05.toShort())))
        assertFalse(instance.isReserved(ElfHalf(0x0005.toShort())))
    }

    companion object {
        @JvmStatic
        fun regularIndicesProvider(): Stream<Arguments> {
            return sequence {
                val increment = 2_000
                val base = 1
                val end = ElfSectionIndex.FIRST_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
                for (value in base until end step increment) {
                    yield(value.toShort())
                }
            }.map { Arguments.of(it) }.asStream()
        }

        @JvmStatic
        fun reservedIndicesProvider(): Stream<Arguments> {
            val start = ElfSectionIndex.FIRST_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
            val end = ElfSectionIndex.LAST_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
            // Use toInt to prevent signed interpretation
            val middle = (start + end) / 2
            return Stream.of(
                Arguments.of(start.toShort()),
                Arguments.of(middle.toShort()),
                Arguments.of(end.toShort())
            )
        }

        @JvmStatic
        fun processorSpecificProvider(): Stream<Arguments> {
            val start = ElfSectionIndex.FIRST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
            val end = ElfSectionIndex.LAST_PROCESSOR_SPECIFIC_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
            // Use toInt to prevent signed interpretation
            val middle = (start + end) / 2
            return Stream.of(
                Arguments.of(start.toShort()),
                Arguments.of(middle.toShort()),
                Arguments.of(end.toShort())
            )
        }

        @JvmStatic
        fun osSpecificProvider(): Stream<Arguments> {
            val start = ElfSectionIndex.FIRST_OS_SPECIFIC_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
            val end = ElfSectionIndex.LAST_OS_SPECIFIC_RESERVED_SECTION_INDEX.half.toInt() and 0xffff
            // Use toInt to prevent signed interpretation
            val middle = (start + end) / 2
            return Stream.of(
                Arguments.of(start.toShort()),
                Arguments.of(middle.toShort()),
                Arguments.of(end.toShort())
            )
        }
    }
}