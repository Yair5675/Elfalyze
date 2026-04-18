package com.yairz.elfalyze.elf.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ElfBitnessTest {

    @ParameterizedTest
    @EnumSource(ElfBitness::class)
    fun `fromBitnessCode should map valid codes to correct enum constants`(expected: ElfBitness) {
        val actual = ElfBitness.fromBitnessCode(expected.bitnessCode)
        assertEquals(expected, actual)
    }

    @Test
    fun `fromBitnessCode should throw IllegalArgumentException for unknown codes`() {
        val unknownCode: Byte = 3

        val exception = assertThrows<IllegalArgumentException> {
            ElfBitness.fromBitnessCode(unknownCode)
        }

        assertEquals("Unknown ElfBitness code: 3", exception.message)
    }

    @Test
    fun `getBitnessCode should return the original byte value`() {
        assertEquals(1.toByte(), ElfBitness.BITNESS_32.bitnessCode)
        assertEquals(2.toByte(), ElfBitness.BITNESS_64.bitnessCode)
    }

    @Test
    fun `fromBitnessCode should handle negative bytes correctly if they are unknown`() {
        // Just to be sure about that signed byte behavior we discussed
        val negativeByte = (-1).toByte()

        assertThrows<IllegalArgumentException> {
            ElfBitness.fromBitnessCode(negativeByte)
        }
    }
}