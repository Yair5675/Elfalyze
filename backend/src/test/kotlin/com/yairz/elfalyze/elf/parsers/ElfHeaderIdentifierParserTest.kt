package com.yairz.elfalyze.elf.parsers

import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException
import java.nio.ByteBuffer
import java.nio.channels.FileChannel

class ElfHeaderIdentifierParserTest {

    private val parser = ElfHeaderIdentifierParser()

    @Test
    fun `parse should return valid ElfHeaderIdentifier when given valid bytes`() {
        // Arrange
        val mockChannel = mockk<FileChannel>()
        val validElfHeader = byteArrayOf(
            0x7f, 0x45, 0x4c, 0x46, // Magic: 0x7F 'E' 'L' 'F'
            0x02,                   // Bitness: 2 (64-bit)
            0x01,                   // Endianness: 1 (Little Endian)
            0x01,                   // Version: 1
            0x00,                   // OS ABI: 0 (System V)
            0x00,                   // ABI Version: 0
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00 // Padding
        )

        every { mockChannel.read(any<ByteBuffer>(), 0L) } answers {
            val buffer = firstArg<ByteBuffer>()
            buffer.put(validElfHeader)
            16 // Return 16 bytes read
        }

        val result = parser.parse(mockChannel)

        assertNotNull(result)
        verify(exactly = 1) { mockChannel.read(any<ByteBuffer>(), 0L) }
    }

    @Test
    fun `parse should throw ElfParsingException when FileChannel throws IOException`() {
        // Arrange
        val mockChannel = mockk<FileChannel>()
        val ioException = IOException("Simulated disk error")

        every { mockChannel.read(any<ByteBuffer>(), 0L) } throws ioException

        // Act & Assert
        val exception = assertThrows<ElfParsingException> {
            parser.parse(mockChannel)
        }

        assertEquals(ioException, exception.cause)
    }

    @Test
    fun `parse should throw ElfParsingException when FileChannel reads less than 16 bytes`() {
        val mockChannel = mockk<FileChannel>()

        every { mockChannel.read(any<ByteBuffer>(), 0L) } answers {
            val buffer = firstArg<ByteBuffer>()
            // Simulate a truncated file by only putting 10 bytes in
            buffer.put(ByteArray(10))
            10 // Return 10 bytes read
        }

        val exception = assertThrows<ElfParsingException> {
            parser.parse(mockChannel)
        }

        assertEquals("Elf file does not contain enough bytes for a header identifier", exception.message)
    }

    @Test
    fun `parse should throw ElfParsingException when model throws IllegalArgumentException`() {
        val mockChannel = mockk<FileChannel>()

        // 0xFF (-1 in Java byte) is likely an invalid bitness/endianness, which should trigger an
        // IllegalArgumentException
        val invalidElfHeader = byteArrayOf(
            0x7f, 0x45, 0x4c, 0x46,
            0xFF.toByte(),          // Invalid bitness
            0x01, 0x01, 0x00, 0x00,
            0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00
        )

        every { mockChannel.read(any<ByteBuffer>(), 0L) } answers {
            val buffer = firstArg<ByteBuffer>()
            buffer.put(invalidElfHeader)
            16
        }

        val exception = assertThrows<ElfParsingException> {
            parser.parse(mockChannel)
        }

        assertTrue(exception.cause is IllegalArgumentException)
        assertTrue(exception.message?.startsWith("Invalid Elf Header Identifier:") == true)
    }
}