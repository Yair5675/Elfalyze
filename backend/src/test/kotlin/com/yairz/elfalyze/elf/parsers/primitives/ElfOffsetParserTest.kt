package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfOffsetParserTest {

    private val parser = ElfOffsetParser()

    @Test
    fun `parse32 should read 4 bytes and convert to unsigned long`() {
        // 0xDEADBEEF
        val rawBytes = byteArrayOf(0xDE.toByte(), 0xAD.toByte(), 0xBE.toByte(), 0xEF.toByte())
        val buffer = ByteBuffer.wrap(rawBytes).order(ByteOrder.BIG_ENDIAN)

        val result = parser.parse32(buffer)

        // 0xDEADBEEF as unsigned is 3735928559L
        assertEquals(3735928559L, result.offset)
        assertEquals(4, buffer.position(), "Buffer position must advance by 4 bytes")
    }

    @Test
    fun `parse32 should throw if buffer is too small`() {
        val buffer = ByteBuffer.allocate(3)

        assertThrows<ElfParsingException> { parser.parse32(buffer) }

        assertEquals(0, buffer.position())
    }

    @Test
    fun `parse64 should read 8 bytes for a full 64-bit offset`() {
        val largeOffset = 0x7FFFFFFFFFFFFFFFL
        val buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putLong(largeOffset)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(largeOffset, result.offset)
        assertEquals(8, buffer.position())
    }

    @Test
    fun `parse64 should throw if buffer has fewer than 8 bytes`() {
        val buffer = ByteBuffer.allocate(7)

        assertThrows<ElfParsingException> { parser.parse64(buffer) }
    }

    @Test
    fun `parsers should respect the ByteOrder of the provided buffer`() {
        // Data: 0x01 0x00 0x00 0x00
        val raw = byteArrayOf(0x01, 0x00, 0x00, 0x00)

        val beBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val leBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse32(beBuffer).offset
        val leValue = parser.parse32(leBuffer).offset

        assertEquals(0x01000000L, beValue, "Big Endian should read 0x01000000")
        assertEquals(1L, leValue, "Little Endian should read 0x00000001")
    }

    @Test
    fun `parse32 handles maximum unsigned 32-bit value`() {
        // 0xFFFFFFFF
        val buffer = ByteBuffer.allocate(4).order(ByteOrder.BIG_ENDIAN)
        buffer.putInt(-1) // 0xFFFFFFFF in signed int
        buffer.flip()

        val result = parser.parse32(buffer)

        assertEquals(4294967295L, result.offset)
    }
}