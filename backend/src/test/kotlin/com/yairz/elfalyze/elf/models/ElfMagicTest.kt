package com.yairz.elfalyze.elf.models

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ElfMagicTest {

    @Test
    fun `fromBytes should create ElfMagic when valid bytes are provided`() {
        val validBytes = byteArrayOf(0x7f, 'E'.code.toByte(), 'L'.code.toByte(), 'F'.code.toByte())

        val result = ElfMagic.fromBytes(validBytes)

        assertNotNull(result)
        assertArrayEquals(validBytes, result.rawMagic)
    }

    @Test
    fun `fromBytes should throw IllegalArgumentException when array size is incorrect`() {
        val tooShort = byteArrayOf(0x7f, 'E'.code.toByte(), 'L'.code.toByte())

        val exception = assertThrows<IllegalArgumentException> {
            ElfMagic.fromBytes(tooShort)
        }

        assertEquals("Magic size must be 4", exception.message)
    }

    @Test
    fun `fromBytes should throw IllegalArgumentException when magic byte 1 is incorrect`() {
        val invalidBytes = byteArrayOf(0x00, 'E'.code.toByte(), 'L'.code.toByte(), 'F'.code.toByte())

        val exception = assertThrows<IllegalArgumentException> {
            ElfMagic.fromBytes(invalidBytes)
        }

        assertTrue(exception.message!!.contains("Invalid magic byte #1"))
    }

    @Test
    fun `fromBytes should throw IllegalArgumentException when magic byte 2 is incorrect`() {
        val invalidBytes = byteArrayOf(0x7f, 'X'.code.toByte(), 'L'.code.toByte(), 'F'.code.toByte())

        val exception = assertThrows<IllegalArgumentException> {
            ElfMagic.fromBytes(invalidBytes)
        }

        assertTrue(exception.message!!.contains("Invalid magic byte #2"))
    }

    @Test
    fun `fromBytes should throw IllegalArgumentException when magic byte 3 is incorrect`() {
        val invalidBytes = byteArrayOf(0x7f, 'E'.code.toByte(), 'X'.code.toByte(), 'F'.code.toByte())

        val exception = assertThrows<IllegalArgumentException> {
            ElfMagic.fromBytes(invalidBytes)
        }

        assertTrue(exception.message!!.contains("Invalid magic byte #3"))
    }

    @Test
    fun `fromBytes should throw IllegalArgumentException when magic byte 4 is incorrect`() {
        val invalidBytes = byteArrayOf(0x7f, 'E'.code.toByte(), 'L'.code.toByte(), 'X'.code.toByte())

        val exception = assertThrows<IllegalArgumentException> {
            ElfMagic.fromBytes(invalidBytes)
        }

        assertTrue(exception.message!!.contains("Invalid magic byte #4"))
    }

    @Test
    fun `getRawMagic should return a new array with correct values`() {
        val magic = ElfMagic(0x7f, 'E'.code.toByte(), 'L'.code.toByte(), 'F'.code.toByte())
        val raw = magic.rawMagic

        assertEquals(4, raw.size)
        assertEquals(0x7f.toByte(), raw[0])
        assertEquals('E'.code.toByte(), raw[1])

        // Ensure it's a copy and doesn't expose internal state if modified
        raw[0] = 0x00
        assertNotEquals(0, magic.rawMagic[0])
    }
}