package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfSignedXWordParserTest {

    private val parser = ElfSignedXWordParser()

    @Test
    fun `parse64 should read 8 bytes as a signed long`() {
        val expectedValue = -123456789012345L
        val buffer = ByteBuffer.allocate(8).order(ByteOrder.BIG_ENDIAN)
        buffer.putLong(expectedValue)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedValue, result.signedXword)
        assertEquals(8, buffer.position(), "Buffer position should advance by 8")
    }

    @Test
    fun `parse64 should throw if buffer has less than 8 bytes`() {
        // Provide only 7 bytes
        val buffer = ByteBuffer.allocate(7)

        assertThrows<ElfParsingException> { parser.parse64(buffer) }
        assertEquals(0, buffer.position(), "Buffer position should not advance on failure")
    }

    @Test
    fun `parse64 should handle large positive values`() {
        val expectedValue = Long.MAX_VALUE
        val buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putLong(expectedValue)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedValue, result.signedXword)
    }

    @Test
    fun `parse64 should correctly handle negative values (two's complement)`() {
        // 0xFFFFFFFFFFFFFFFF is -1 in signed 64-bit
        val rawBytes = ByteArray(8) { 0xFF.toByte() }
        val buffer = ByteBuffer.wrap(rawBytes)

        val result = parser.parse64(buffer)

        assertEquals(-1L, result.signedXword)
    }

    @Test
    fun `parse64 should respect the ByteOrder of the provided buffer`() {
        // Data: 0x00...01
        val raw = ByteArray(8)
        raw[7] = 1

        val beBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val leBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse64(beBuffer).signedXword
        val leValue = parser.parse64(leBuffer).signedXword

        assertEquals(1L, beValue, "Big Endian should read 1")
        // In Little Endian, the '1' at the end is the most significant byte
        assertEquals(0x0100000000000000L, leValue, "Little Endian should read 0x01 at the MSB")
    }
}