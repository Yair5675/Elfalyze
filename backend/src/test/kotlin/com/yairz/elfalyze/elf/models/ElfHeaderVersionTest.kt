package com.yairz.elfalyze.elf.models

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

class ElfHeaderVersionTest {

    @ParameterizedTest
    @EnumSource(ElfHeaderVersion::class)
    fun `fromVersionCode should successfully map all known version variants`(version: ElfHeaderVersion) {
        val actual = ElfHeaderVersion.fromVersionCode(version.versionCode)
        assertEquals(version, actual)
    }

    @Test
    fun `fromVersionCode should throw IllegalArgumentException for unsupported versions`() {
        // Version 2 doesn't officially exist in the ELF spec (yet?)
        val unsupportedVersion: Byte = 2

        val exception = assertThrows<IllegalArgumentException> {
            ElfHeaderVersion.fromVersionCode(unsupportedVersion)
        }

        assertEquals("Unknown version code: 2", exception.message)
    }

    @Test
    fun `getVersionCode should return the assigned byte for the version`() {
        assertEquals(0.toByte(), ElfHeaderVersion.INVALID.versionCode)
        assertEquals(1.toByte(), ElfHeaderVersion.CURRENT.versionCode)
    }

    @Test
    fun `fromVersionCode should reject negative byte values`() {
        val rogueByte = (-128).toByte()

        assertThrows<IllegalArgumentException> {
            ElfHeaderVersion.fromVersionCode(rogueByte)
        }
    }
}