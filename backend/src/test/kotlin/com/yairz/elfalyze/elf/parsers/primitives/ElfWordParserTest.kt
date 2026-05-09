package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.elf.types.ElfWord
import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfWordParserTest {

    private val parser = ElfWordParser()

    @Test
    fun `parse32 should read 4 bytes and preserve bit pattern for unsigned interpretation`() {
        // 0xFFFFFFFF: -1 as signed int, 4294967295 as unsigned
        val rawBytes = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
        val buffer = ByteBuffer.wrap(rawBytes).order(ByteOrder.BIG_ENDIAN)

        val result = parser.parse32(buffer)

        assertEquals(-1, result.word, "Bits should be preserved exactly")
        assertEquals(4, buffer.position(), "Buffer position should advance by 4")
    }

    @Test
    fun `parse32 should throw if buffer has less than 4 bytes`() {
        val buffer = ByteBuffer.allocate(3)
        assertThrows<ElfParsingException> { parser.parse32(buffer) }
        assertEquals(0, buffer.position())
    }

    @Test
    fun `parse64 should also read 4 bytes for Word type`() {
        val expectedValue = 0x12345678
        val buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putInt(expectedValue)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedValue, result.word)
        assertEquals(4, buffer.position())
    }

    @Test
    fun `parse64 should throw if buffer is too small`() {
        val buffer = ByteBuffer.allocate(2)
        assertThrows<ElfParsingException> { parser.parse64(buffer) }
    }

    @Test
    fun `parsers should respect the ByteOrder of the provided buffer`() {
        // Data: 0x00 0x00 0x00 0x01
        val raw = byteArrayOf(0, 0, 0, 1)

        val beBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val leBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse32(beBuffer).word
        val leValue = parser.parse32(leBuffer).word

        assertEquals(1, beValue, "Big Endian should result in 1")
        assertEquals(0x01000000, leValue, "Little Endian should result in 0x01000000")
    }

    @Test
    fun `parsed values should be comparable using unsigned logic`() {
        // 0x00000001 (1) vs 0xFFFFFFFF (treated as -1 in signed, but larger in unsigned)
        val word1 = ElfWord(1)
        val word2 = ElfWord(-1)

        val comparison = word1.compareTo(word2)

        assertTrue(comparison < 0, "1 should be less than 4294967295 in unsigned comparison")
    }
}