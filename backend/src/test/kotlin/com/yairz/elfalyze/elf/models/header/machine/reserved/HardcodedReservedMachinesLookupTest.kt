package com.yairz.elfalyze.elf.models.header.machine.reserved

import com.yairz.elfalyze.elf.models.header.machine.reserved.HardcodedReservedMachinesLookup.InclusiveReservedMachineRange
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.util.Optional

class HardcodedReservedMachinesLookupTest {

    private lateinit var lookup: HardcodedReservedMachinesLookup

    @BeforeEach
    fun setUp() {
        // Create custom, predictable ranges for testing
        // Note: Ranges must be sorted to satisfy the assumptions in the Java implementation
        val customRanges = listOf(
            InclusiveReservedMachineRange(10),                   // Single value, no owner
            InclusiveReservedMachineRange(20, "OwnerA"),         // Single value, with owner
            InclusiveReservedMachineRange(30, 40),               // Range, no owner
            InclusiveReservedMachineRange(50, 60, "OwnerB"),     // Range, with owner
            InclusiveReservedMachineRange(65000, 65010, "High")  // Unsigned large values
        )

        lookup = HardcodedReservedMachinesLookup(customRanges)
    }

    // --- Tests for isReservedMachine ---

    @Test
    fun `isReservedMachine returns true for single exact matches`() {
        assertTrue(lookup.isReservedMachine(10.toShort()))
        assertTrue(lookup.isReservedMachine(20.toShort()))
    }

    @Test
    fun `isReservedMachine returns true for values inside inclusive ranges`() {
        // Start of range
        assertTrue(lookup.isReservedMachine(30.toShort()))
        assertTrue(lookup.isReservedMachine(50.toShort()))

        // Middle of range
        assertTrue(lookup.isReservedMachine(35.toShort()))
        assertTrue(lookup.isReservedMachine(55.toShort()))

        // End of range
        assertTrue(lookup.isReservedMachine(40.toShort()))
        assertTrue(lookup.isReservedMachine(60.toShort()))
    }

    @Test
    fun `isReservedMachine returns true for high unsigned short values`() {
        assertTrue(lookup.isReservedMachine(65005.toShort()))
    }

    @Test
    fun `isReservedMachine returns false for values outside of any range`() {
        assertFalse(lookup.isReservedMachine(5.toShort()))    // Below all ranges
        assertFalse(lookup.isReservedMachine(15.toShort()))   // Between 10 and 20
        assertFalse(lookup.isReservedMachine(25.toShort()))   // Between 20 and 30
        assertFalse(lookup.isReservedMachine(45.toShort()))   // Between 40 and 50
        assertFalse(lookup.isReservedMachine(61.toShort()))   // Just after 50-60 range
        assertFalse(lookup.isReservedMachine(65535.toShort()))// Above all ranges
    }

    // --- Tests for getReservedMachineOwner ---

    @Test
    fun `getReservedMachineOwner returns owner when present in single value`() {
        val owner = lookup.getReservedMachineOwner(20.toShort())
        assertTrue(owner.isPresent)
        assertEquals("OwnerA", owner.get())
    }

    @Test
    fun `getReservedMachineOwner returns owner when present in a range`() {
        // Test boundaries and inside the range
        assertEquals(Optional.of("OwnerB"), lookup.getReservedMachineOwner(50.toShort()))
        assertEquals(Optional.of("OwnerB"), lookup.getReservedMachineOwner(55.toShort()))
        assertEquals(Optional.of("OwnerB"), lookup.getReservedMachineOwner(60.toShort()))
    }

    @Test
    fun `getReservedMachineOwner returns owner for high unsigned short values`() {
        assertEquals(Optional.of("High"), lookup.getReservedMachineOwner(65005.toShort()))
    }

    @Test
    fun `getReservedMachineOwner returns empty when machine is reserved but has no owner`() {
        assertEquals(Optional.empty<String>(), lookup.getReservedMachineOwner(10.toShort()))
        assertEquals(Optional.empty<String>(), lookup.getReservedMachineOwner(35.toShort()))
    }

    @Test
    fun `getReservedMachineOwner returns empty when machine is not reserved`() {
        assertEquals(Optional.empty<String>(), lookup.getReservedMachineOwner(5.toShort()))
        assertEquals(Optional.empty<String>(), lookup.getReservedMachineOwner(15.toShort()))
        assertEquals(Optional.empty<String>(), lookup.getReservedMachineOwner(100.toShort()))
    }

    // --- Test for Singleton (getInstance) ---

    @Test
    fun `getInstance returns non-null singleton instance`() {
        val instance1 = HardcodedReservedMachinesLookup.getInstance()
        val instance2 = HardcodedReservedMachinesLookup.getInstance()

        assertNotNull(instance1)
        assertTrue(instance1 === instance2, "getInstance should return the exact same reference")
    }
}