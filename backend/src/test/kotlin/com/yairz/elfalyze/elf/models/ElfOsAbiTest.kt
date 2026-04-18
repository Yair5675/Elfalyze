package com.yairz.elfalyze.elf.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

class ElfOsAbiTest {

    @ParameterizedTest
    @CsvSource(
        "0, None",
        "3, GNU",
        "9, FreeBSD",
        "18, Stratus Technologies OpenVOS"
    )
    fun `fromOsAbiCode should map known standard codes to correct names`(code: Byte, expectedName: String) {
        val result = ElfOsAbi.fromOsAbiCode(code)

        assertEquals(code.toInt(), result.osAbiCode)
        assertEquals(expectedName, result.osAbiName)
    }

    @ParameterizedTest
    @ValueSource(bytes = [64, 100, 127])
    fun `fromOsAbiCode should allow architecture specific codes starting from 64`(code: Byte) {
        val result = ElfOsAbi.fromOsAbiCode(code)

        assertEquals(code.toInt(), result.osAbiCode)
        assertEquals("Architecture Specific", result.osAbiName)
    }

    @Test
    fun `fromOsAbiCode should handle unsigned bytes correctly for high values`() {
        // 0xFE is 254 unsigned, which is > 64
        val highCode = 0xFE.toByte()

        val result = ElfOsAbi.fromOsAbiCode(highCode)

        assertEquals(254, result.osAbiCode)
        assertEquals("Architecture Specific", result.osAbiName)
    }

    @Test
    fun `fromOsAbiCode should throw IllegalArgumentException for undefined codes below 64`() {
        val undefinedCode: Byte = 4 // 4 is not a registered code

        val exception = assertThrows<IllegalArgumentException> {
            ElfOsAbi.fromOsAbiCode(undefinedCode)
        }

        assertEquals("Invalid OS-ABI code: 4", exception.message)
    }

    @Test
    fun `toString should return formatted name and code`() {
        val abi = ElfOsAbi.fromOsAbiCode(3.toByte())

        assertEquals("GNU (3)", abi.toString())
    }

    @Test
    fun `getOsAbiName should return Architecture Specific for codes without explicit names`() {
        // Using 65 which is above the architecture-specific threshold
        val abi = ElfOsAbi(65)

        assertEquals("Architecture Specific", abi.osAbiName)
    }
}