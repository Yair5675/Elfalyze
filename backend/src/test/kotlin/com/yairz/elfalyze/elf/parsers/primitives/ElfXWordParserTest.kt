package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.elf.types.ElfXWord
import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfXWordParserTest {

    private val parser = ElfXWordParser()

    @Test
    fun `parse64 should read 8 bytes and preserve bit pattern`() {
        // 0xFFFFFFFFFFFFFFFF (Max unsigned 64-bit value)
        val rawBytes = ByteArray(8) { 0xFF.toByte() }
        val buffer = ByteBuffer.wrap(rawBytes).order(ByteOrder.BIG_ENDIAN)

        val result = parser.parse64(buffer)

        // In Java/Kotlin, the bit pattern for max unsigned long is represented as -1L in a signed long
        assertEquals(-1L, result.xword)
        assertEquals(8, buffer.position(), "Buffer position should advance by 8")
    }

    @Test
    fun `parse64 should throw if buffer has less than 8 bytes`() {
        val buffer = ByteBuffer.allocate(7)

        assertThrows<ElfParsingException> { parser.parse64(buffer) }

        assertEquals(0, buffer.position(), "Buffer position should not advance on failure")
    }

    @Test
    fun `parse64 should respect the ByteOrder of the provided buffer`() {
        // Data: 0x00 0x00 ... 0x01
        val raw = ByteArray(8).apply { this[7] = 1 }

        val beBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val leBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse64(beBuffer).xword
        val leValue = parser.parse64(leBuffer).xword

        assertEquals(1L, beValue, "Big Endian should read 1")
        // In Little Endian, the '1' at index 7 is the most significant byte
        assertEquals(0x0100000000000000, leValue, "Little Endian should read 0x01 at the MSB")
    }

    @Test
    fun `ElfXWord should use unsigned comparison logic`() {
        // word1 is 1, word2 is 0xFFFFFFFFFFFFFFFF (which is -1L)
        val word1 = ElfXWord(1L)
        val word2 = ElfXWord(-1L)

        // Unsigned: 1 < 18446744073709551615
        val comparison = word1.compareTo(word2)

        assertTrue(comparison < 0, "1 should be less than Max Unsigned Long in unsigned comparison")
    }

    @Test
    fun `parse64 should handle middle-range values correctly`() {
        val expectedValue = 0x1234567890ABCDEFL
        val buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN)
        buffer.putLong(expectedValue)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedValue, result.xword)
    }
}