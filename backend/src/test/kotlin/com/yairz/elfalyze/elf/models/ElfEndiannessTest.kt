package com.yairz.elfalyze.elf.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ElfEndiannessTest {

    @ParameterizedTest
    @EnumSource(ElfEndianness::class)
    fun `fromEndiannessCode should correctly map every enum variant to its code`(endianness: ElfEndianness) {
        val actual = ElfEndianness.fromEndiannessCode(endianness.endiannessCode)
        assertEquals(endianness, actual)
    }

    @Test
    fun `fromEndiannessCode should throw IllegalArgumentException for undefined codes`() {
        val undefinedCode: Byte = 3

        val exception = assertThrows<IllegalArgumentException> {
            ElfEndianness.fromEndiannessCode(undefinedCode)
        }

        assertEquals("Invalid endianness code: 3", exception.message)
    }

    @Test
    fun `getEndiannessCode should return the raw byte value assigned to the constant`() {
        assertEquals(1.toByte(), ElfEndianness.LSB.endiannessCode)
        assertEquals(2.toByte(), ElfEndianness.MSB.endiannessCode)
    }

    @Test
    fun `fromEndiannessCode should fail for negative values`() {
        // Ensuring the signed-byte trap doesn't accidentally match if we ever added high-bit constants
        val negativeByte = (-1).toByte()

        assertThrows<IllegalArgumentException> {
            ElfEndianness.fromEndiannessCode(negativeByte)
        }
    }
}