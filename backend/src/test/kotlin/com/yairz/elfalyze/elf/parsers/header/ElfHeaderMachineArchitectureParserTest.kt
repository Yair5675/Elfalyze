package com.yairz.elfalyze.elf.parsers.header

import com.yairz.elfalyze.elf.models.ElfBitness
import com.yairz.elfalyze.elf.models.header.machine.registered.RegisteredMachinesLookup
import com.yairz.elfalyze.elf.models.header.machine.reserved.ReservedMachinesLookup
import com.yairz.elfalyze.elf.parsers.primitives.ElfPrimitivesParser
import com.yairz.elfalyze.elf.types.ElfHalf
import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.eq
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.nio.ByteBuffer
import java.util.Optional

class ElfHeaderMachineArchitectureParserTest {

    @Mock
    private lateinit var registeredMachinesLookup: RegisteredMachinesLookup

    @Mock
    private lateinit var reservedMachinesLookup: ReservedMachinesLookup

    @Mock
    private lateinit var primitivesParser: ElfPrimitivesParser

    private lateinit var parser: ElfHeaderMachineArchitectureParser

    private val dummyBuffer = ByteBuffer.allocate(2)
    private val bitness = ElfBitness.BITNESS_64

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)
        parser = ElfHeaderMachineArchitectureParser(
            registeredMachinesLookup,
            reservedMachinesLookup,
            primitivesParser
        )
    }

    private fun mockPrimitiveParse(value: Short) {
        `when`(primitivesParser.parsePrimitive(eq(dummyBuffer), eq(ElfHalf::class.java), eq(bitness)))
            .thenReturn(ElfHalf(value))
    }

    @Test
    fun `parseMachineArchitecture returns registered machine with known name`() {
        val machineNum: Short = 0x3E // AMD x86-64
        mockPrimitiveParse(machineNum)

        val machineName = "Advanced Micro Devices X86-64"
        `when`(registeredMachinesLookup.isDefinedMachineNumber(machineNum)).thenReturn(true)
        `when`(registeredMachinesLookup.getDefinedMachineName(machineNum))
            .thenReturn(Optional.of(machineName))

        val result = parser.parseMachineArchitecture(dummyBuffer, bitness)

        assertTrue(result.isRegisteredMachine)
        assertFalse(result.isReservedMachine)
        assertEquals(machineNum, result.rawMachine().half())
        assertEquals(machineName, result.machineName())
    }

    @Test
    fun `parseMachineArchitecture returns nameless string when registered machine has no name`() {
        val machineNum: Short = 0x01
        mockPrimitiveParse(machineNum)

        `when`(registeredMachinesLookup.isDefinedMachineNumber(machineNum)).thenReturn(true)
        `when`(registeredMachinesLookup.getDefinedMachineName(machineNum)).thenReturn(Optional.empty())

        val result = parser.parseMachineArchitecture(dummyBuffer, bitness)

        assertTrue(result.isRegisteredMachine)
        assertEquals("Nameless registered machine", result.machineName())
    }

    @Test
    fun `parseMachineArchitecture returns reserved machine with owner name`() {
        val machineNum: Short = 0x10
        mockPrimitiveParse(machineNum)

        `when`(registeredMachinesLookup.isDefinedMachineNumber(machineNum)).thenReturn(false)
        `when`(reservedMachinesLookup.isReservedMachine(machineNum)).thenReturn(true)
        `when`(reservedMachinesLookup.getReservedMachineOwner(machineNum))
            .thenReturn(Optional.of(ReservedMachinesLookup.ARM_OWNER))

        val result = parser.parseMachineArchitecture(dummyBuffer, bitness)

        assertFalse(result.isRegisteredMachine)
        assertTrue(result.isReservedMachine)
        assertEquals(ReservedMachinesLookup.ARM_OWNER, result.machineName())
    }

    @Test
    fun `parseMachineArchitecture returns default string when reserved machine has no owner`() {
        val machineNum: Short = 0x11
        mockPrimitiveParse(machineNum)

        `when`(registeredMachinesLookup.isDefinedMachineNumber(machineNum)).thenReturn(false)
        `when`(reservedMachinesLookup.isReservedMachine(machineNum)).thenReturn(true)
        `when`(reservedMachinesLookup.getReservedMachineOwner(machineNum)).thenReturn(Optional.empty())

        val result = parser.parseMachineArchitecture(dummyBuffer, bitness)

        assertFalse(result.isRegisteredMachine)
        assertEquals("Reserved (no owner)", result.machineName())
    }

    @Test
    fun `parseMachineArchitecture throws ElfParsingException if neither registered nor reserved`() {
        val machineNum: Short = 0x99
        mockPrimitiveParse(machineNum)

        // It is neither registered nor reserved
        `when`(registeredMachinesLookup.isDefinedMachineNumber(machineNum)).thenReturn(false)
        `when`(reservedMachinesLookup.isReservedMachine(machineNum)).thenReturn(false)

        val exception = assertThrows<ElfParsingException> {
            parser.parseMachineArchitecture(dummyBuffer, bitness)
        }

        assertTrue(exception.message!!.contains("Invalid machine architecture number"))
    }

    @Test
    fun `parseMachineArchitecture propagates ElfParsingException from primitivesParser`() {
        val expectedMessage = "Buffer too small to read ElfHalf"

        `when`(primitivesParser.parsePrimitive(eq(dummyBuffer), eq(ElfHalf::class.java), eq(bitness)))
            .thenThrow(ElfParsingException(expectedMessage))

        val exception = assertThrows<ElfParsingException> {
            parser.parseMachineArchitecture(dummyBuffer, bitness)
        }

        assertEquals(expectedMessage, exception.message)
    }
}