package com.yairz.elfalyze.elf.models.header

import com.yairz.elfalyze.elf.types.ElfHalf
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class ElfHeaderTypeTest {

    @ParameterizedTest(name = "Value {0} should map to {1}")
    @CsvSource(
        "0, No File Type",
        "1, Relocatable File",
        "2, Executable File",
        "3, Shared Object File",
        "4, Core File"
    )
    fun `identifies standard ELF header types`(value: Short, expectedName: String) {
        val raw = ElfHalf(value)
        val headerType = ElfHeaderType.fromHeaderTypeValue(raw)

        assertEquals(expectedName, headerType.headerTypeName)
        assertFalse(headerType.isOsSpecific)
        assertFalse(headerType.isProcessorSpecific)
    }

    @Test
    fun `identifies OS-Specific boundaries`() {
        val start = ElfHeaderType.fromHeaderTypeValue(ElfHalf(0xfe00.toShort()))
        val end = ElfHeaderType.fromHeaderTypeValue(ElfHalf(0xfeff.toShort()))

        assertTrue(start.isOsSpecific)
        assertEquals("OS-Specific", start.headerTypeName)

        assertTrue(end.isOsSpecific)
        assertEquals("OS-Specific", end.headerTypeName)
    }

    @Test
    fun `identifies Processor-Specific boundaries`() {
        // 0xffff is -1 in signed short
        val start = ElfHeaderType.fromHeaderTypeValue(ElfHalf(0xff00.toShort()))
        val end = ElfHeaderType.fromHeaderTypeValue(ElfHalf(0xffff.toShort()))

        assertTrue(start.isProcessorSpecific)
        assertEquals("Processor-Specific", start.headerTypeName)

        assertTrue(end.isProcessorSpecific)
        assertEquals("Processor-Specific", end.headerTypeName)
    }

    @Test
    fun `throws exception for invalid type values`() {
        val invalidValue = ElfHalf(5.toShort())

        val exception = assertThrows<IllegalArgumentException> {
            ElfHeaderType.fromHeaderTypeValue(invalidValue)
        }

        assertTrue(exception.message!!.contains("0x0005"))
    }

    @Test
    fun `toString formats correctly`() {
        val execType = ElfHeaderType(ElfHalf(2.toShort()))
        assertEquals("Executable File (0x0002)", execType.toString())

        val procType = ElfHeaderType(ElfHalf(0xff01.toShort()))
        assertEquals("Processor-Specific (0xff01)", procType.toString())
    }

    @Test
    fun `constructor correctly assigns raw type`() {
        val raw = ElfHalf(1.toShort())
        val headerType = ElfHeaderType(raw)

        assertEquals(raw, headerType.rawType)
    }
}
