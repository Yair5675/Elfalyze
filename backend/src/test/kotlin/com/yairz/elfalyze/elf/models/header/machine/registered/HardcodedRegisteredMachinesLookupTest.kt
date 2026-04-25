package com.yairz.elfalyze.elf.models.header.machine.registered

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.*

class HardcodedRegisteredMachinesLookupTest {

    private lateinit var lookup: HardcodedRegisteredMachinesLookup

    private val testMachineId: Short = 42
    private val testMachineName = "Test Architecture"
    private val unknownMachineId: Short = 999

    @BeforeEach
    fun setUp() {
        // Inject a custom map to isolate the test from the hardcoded production data
        val testData = mapOf(testMachineId to testMachineName)
        lookup = HardcodedRegisteredMachinesLookup(testData)
    }

    @Test
    fun `isDefinedMachineNumber returns true for known machine ID`() {
        assertTrue(lookup.isDefinedMachineNumber(testMachineId))
    }

    @Test
    fun `isDefinedMachineNumber returns false for unknown machine ID`() {
        assertFalse(lookup.isDefinedMachineNumber(unknownMachineId))
    }

    @Test
    fun `getDefinedMachineName returns correct name for known machine ID`() {
        val result = lookup.getDefinedMachineName(testMachineId)

        assertTrue(result.isPresent)
        assertEquals(testMachineName, result.get())
    }

    @Test
    fun `getDefinedMachineName returns empty optional for unknown machine ID`() {
        val result = lookup.getDefinedMachineName(unknownMachineId)

        assertEquals(Optional.empty<String>(), result)
    }

    @Test
    fun `getInstance returns singleton instance with production data`() {
        val instance1 = HardcodedRegisteredMachinesLookup.getInstance()
        val instance2 = HardcodedRegisteredMachinesLookup.getInstance()

        assertNotNull(instance1)
        // Verify it is a singleton by checking reference equality
        assertTrue(instance1 === instance2)

        // Sanity check that production data is loaded (e.g., x86-64)
        assertTrue(instance1.isDefinedMachineNumber(62.toShort()))
    }
}