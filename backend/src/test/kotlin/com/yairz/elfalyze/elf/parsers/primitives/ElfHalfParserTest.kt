package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfHalfParserTest {

    private val parser = ElfHalfParser()

    @Test
    fun `parse32 should read 2 bytes and preserve the bit pattern`() {
        // 0x1234
        val rawBytes = byteArrayOf(0x12, 0x34)
        val buffer = ByteBuffer.wrap(rawBytes).order(ByteOrder.BIG_ENDIAN)

        val result = parser.parse32(buffer)

        assertEquals(0x1234.toShort(), result.half)
        assertEquals(2, buffer.position(), "Buffer position should advance by 2")
    }

    @Test
    fun `parse32 should throw if buffer has insufficient bytes`() {
        val buffer = ByteBuffer.allocate(1)

        assertThrows<ElfParsingException> { parser.parse32(buffer) }

        assertEquals(0, buffer.position(), "Buffer position should not advance on failure")
    }

    @Test
    fun `parse64 should read 2 bytes correctly`() {
        val expectedValue: Short = 0x55AA.toShort()
        val buffer = ByteBuffer.allocate(2).order(ByteOrder.BIG_ENDIAN)
        buffer.putShort(expectedValue)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedValue, result.half)
        assertEquals(2, buffer.position())
    }

    @Test
    fun `parse64 should throw if buffer has insufficient bytes`() {
        val buffer = ByteBuffer.allocate(1)

        assertThrows<ElfParsingException> { parser.parse64(buffer) }
    }

    @Test
    fun `parsers should handle unsigned overflow bit patterns correctly`() {
        // 0xFFFF is 65535 unsigned, which is -1 in a signed short
        val rawBytes = byteArrayOf(0xFF.toByte(), 0xFF.toByte())
        val buffer = ByteBuffer.wrap(rawBytes)

        val result = parser.parse32(buffer)

        assertEquals((-1).toShort(), result.half)
    }

    @Test
    fun `parsers should respect the endianness set on the ByteBuffer`() {
        // Data: 0x00 0x01
        val raw = byteArrayOf(0x00, 0x01)

        val bigEndianBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val littleEndianBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse32(bigEndianBuffer).half
        val leValue = parser.parse32(littleEndianBuffer).half

        assertEquals(1.toShort(), beValue, "Big Endian should read 0x0001")
        assertEquals(0x0100.toShort(), leValue, "Little Endian should read 0x0100")
    }
}