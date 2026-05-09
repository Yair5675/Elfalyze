package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ElfAddressParserTest {

    private val parser = ElfAddressParser()

    @Test
    fun `parse32 should read 4 bytes and treat them as an unsigned long`() {
        // 0xFFFFFFFF as a signed Int is -1, but as an Elf32_Addr it should be 4294967295L
        val rawBytes = byteArrayOf(0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte(), 0xFF.toByte())
        val buffer = ByteBuffer.wrap(rawBytes).order(ByteOrder.BIG_ENDIAN)

        val result = parser.parse32(buffer)

        assertEquals(4294967295L, result.address)
        assertEquals(4, buffer.position(), "Buffer position should advance by 4")
    }

    @Test
    fun `parse32 should throw if buffer has less than 4 bytes remaining`() {
        val buffer = ByteBuffer.allocate(3)

        assertThrows<ElfParsingException> {
            parser.parse32(buffer)
        }

        assertEquals(0, buffer.position(), "Buffer position should not advance on failure")
    }

    @Test
    fun `parse64 should read 8 bytes correctly`() {
        val expectedAddress = 0x1122334455667788L
        val buffer = ByteBuffer.allocate(8).order(ByteOrder.LITTLE_ENDIAN)
        buffer.putLong(expectedAddress)
        buffer.flip()

        val result = parser.parse64(buffer)

        assertEquals(expectedAddress, result.address)
        assertEquals(8, buffer.position())
    }

    @Test
    fun `parse64 should throw if buffer has less than 8 bytes remaining`() {
        val buffer = ByteBuffer.allocate(7)

        assertThrows<ElfParsingException> {
            parser.parse64(buffer)
        }
    }

    @Test
    fun `parsers should respect the endianness set on the ByteBuffer`() {
        // Data: 0x00 0x00 0x00 0x01
        val raw = byteArrayOf(0, 0, 0, 1)

        val bigEndianBuffer = ByteBuffer.wrap(raw).order(ByteOrder.BIG_ENDIAN)
        val littleEndianBuffer = ByteBuffer.wrap(raw).order(ByteOrder.LITTLE_ENDIAN)

        val beValue = parser.parse32(bigEndianBuffer).address
        val leValue = parser.parse32(littleEndianBuffer).address

        assertEquals(1L, beValue, "Big Endian should result in 1")
        assertEquals(16777216L, leValue, "Little Endian should result in 0x01000000")
    }
}