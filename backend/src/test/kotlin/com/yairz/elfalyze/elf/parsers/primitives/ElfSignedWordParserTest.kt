package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfSignedWordParserTest {

    private val parser = ElfSignedWordParser()

    @Test
    fun `parse32 should read 4 bytes as a signed integer`() {
        val expectedValue = -123456
        val buffer = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN)
        buffer.putInt(expectedValue)
        buffer.flip()

        val result = parser.parse32(buffer)

        assertEquals(expectedValue, result.signedWord)
        assertEquals(4, buffer.position(), "Buffer position should advance by 4")
    }

    @Test
    fun `parse32 should throw if buffer has less than 4 bytes`() {
        val buffer = ByteBuffer.allocate(3)
        assertThrows<ElfParsingException> { parser.parse32(buffer) }

        assertEquals(0, buffer.position())
    }

    @Test
    fun `parse64 should also read 4 bytes as per ELF spec for Sword`() {
        // Even in 64-bit context, Elf64_Sword is 4 bytes
        val expectedValue = 0x12345678
        val buffer = ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putInt(expectedValue)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedValue, result.signedWord)
        assertEquals(4, buffer.position())
    }

    @Test
    fun `parse64 should throw if buffer has less than 4 bytes`() {
        val buffer = ByteBuffer.allocate(3)
        assertThrows<ElfParsingException> { parser.parse64(buffer) }
    }

    @Test
    fun `parsers should correctly handle negative values`() {
        // 0xFFFFFFFF is -1 in a signed 32-bit integer
        val rawBytes = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
        val buffer = ByteBuffer.wrap(rawBytes)

        val result = parser.parse32(buffer)

        assertEquals(-1, result.signedWord)
    }

    @Test
    fun `parsers should respect the ByteOrder of the provided buffer`() {
        // Data: 0x00 0x00 0x00 0x01
        val raw = byteArrayOf(0, 0, 0, 1)

        val beBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val leBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse32(beBuffer).signedWord
        val leValue = parser.parse32(leBuffer).signedWord

        assertEquals(1, beValue, "Big Endian should read 0x00000001")
        assertEquals(0x01000000, leValue, "Little Endian should read 0x01000000")
    }
}