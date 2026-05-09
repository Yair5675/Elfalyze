package com.yairz.elfalyze.elf.parsers.primitives

import com.yairz.elfalyze.elf.models.ElfBitness
import com.yairz.elfalyze.elf.types.*
import com.yairz.elfalyze.exceptions.elf.ElfParsingException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.nio.ByteBuffer
import java.util.*

class ElfPrimitivesParserTest {

    // 32-bit Mocks
    @Mock
    lateinit var address32Parser: Elf32PrimitiveParser<ElfAddress>
    @Mock
    lateinit var half32Parser: Elf32PrimitiveParser<ElfHalf>
    @Mock
    lateinit var offset32Parser: Elf32PrimitiveParser<ElfOffset>
    @Mock
    lateinit var signedWord32Parser: Elf32PrimitiveParser<ElfSignedWord>
    @Mock
    lateinit var word32Parser: Elf32PrimitiveParser<ElfWord>

    // 64-bit Mocks
    @Mock
    lateinit var address64Parser: Elf64PrimitiveParser<ElfAddress>
    @Mock
    lateinit var half64Parser: Elf64PrimitiveParser<ElfHalf>
    @Mock
    lateinit var offset64Parser: Elf64PrimitiveParser<ElfOffset>
    @Mock
    lateinit var signedWord64Parser: Elf64PrimitiveParser<ElfSignedWord>
    @Mock
    lateinit var signedXWord64Parser: Elf64PrimitiveParser<ElfSignedXWord>
    @Mock
    lateinit var word64Parser: Elf64PrimitiveParser<ElfWord>
    @Mock
    lateinit var xWord64Parser: Elf64PrimitiveParser<ElfXWord>

    private lateinit var parserDispatcher: ElfPrimitivesParser

    @BeforeEach
    fun setup() {
        MockitoAnnotations.openMocks(this)

        parserDispatcher = ElfPrimitivesParser(
            address32Parser,
            half32Parser,
            offset32Parser,
            signedWord32Parser,
            word32Parser,
            address64Parser,
            half64Parser,
            offset64Parser,
            signedWord64Parser,
            signedXWord64Parser,
            word64Parser,
            xWord64Parser
        )
    }

    @Test
    fun `parsePrimitive routes to 32-bit parser when BITNESS_32 is requested`() {
        val buffer = ByteBuffer.allocate(4)
        val expectedWord = ElfWord(42)

        `when`(word32Parser.parse32(buffer)).thenReturn(expectedWord)

        val result = parserDispatcher.parsePrimitive(buffer, ElfWord::class.java, ElfBitness.BITNESS_32)

        assertEquals(expectedWord, result)
        verify(word32Parser).parse32(buffer)
        verifyNoInteractions(word64Parser) // Ensure it didn't call the 64-bit equivalent
    }

    @Test
    fun `parsePrimitive routes to 64-bit parser when BITNESS_64 is requested`() {
        val buffer = ByteBuffer.allocate(8)
        val expectedXWord = ElfXWord(123456789L)

        `when`(xWord64Parser.parse64(buffer)).thenReturn(expectedXWord)

        val result = parserDispatcher.parsePrimitive(buffer, ElfXWord::class.java, ElfBitness.BITNESS_64)

        assertEquals(expectedXWord, result)
        verify(xWord64Parser).parse64(buffer)
    }

    @Test
    fun `parsePrimitive throws if underlying parser fails`() {
        val buffer = ByteBuffer.allocate(2) // Not enough bytes

        `when`(address64Parser.parse64(buffer)).thenThrow(ElfParsingException())

        assertThrows<ElfParsingException> { parserDispatcher.parsePrimitive(buffer, ElfAddress::class.java, ElfBitness.BITNESS_64)  }

        verify(address64Parser).parse64(buffer)
    }

    @Test
    fun `parsePrimitive throws when requesting 64-bit only type with 32-bit bitness`() {
        val buffer = ByteBuffer.allocate(8)

        // ElfXWord implements Elf64Primitive, but NOT Elf32Primitive.
        // Therefore, Elf32Primitive.class.isAssignableFrom(ElfXWord.class) will be false.
        assertThrows<ElfParsingException> { parserDispatcher.parsePrimitive(buffer, ElfXWord::class.java, ElfBitness.BITNESS_32) }

        verifyNoInteractions(xWord64Parser) // Parser should never be reached
    }
}