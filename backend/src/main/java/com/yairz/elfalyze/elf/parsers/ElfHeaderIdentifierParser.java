package com.yairz.elfalyze.elf.parsers;

import com.yairz.elfalyze.elf.models.*;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Component
public class ElfHeaderIdentifierParser {
    /*
        Size of the entire elf header identifier.
        Bytes 1-4: Magic
        Byte 5: Bitness
        Byte 6: Endianness
        Byte 7: Header Version
        Byte 8: OS ABI Specification
        Byte 9: OS ABI Version
        Byte 10-16: Unused Padding
     */
    private static final int ELF_HEADER_IDENTIFIER_SIZE = 16;
    private static final int ELF_MAGIC_INDEX = 0;
    private static final int ELF_BITNESS_INDEX = 4;
    private static final int ELF_ENDIANNESS_INDEX = 5;
    private static final int ELF_VERSION_INDEX = 6;
    private static final int ELF_OS_ABI_INDEX = 7;
    private static final int ELF_OS_ABI_VERSION_INDEX = 8;

    public ElfHeaderIdentifier parse(FileChannel elfFile) throws ElfParsingException {
        ByteBuffer rawIdentifier = readIdentifierFromFile(elfFile);
        try {
            return new ElfHeaderIdentifier(
                    parseMagic(rawIdentifier),
                    parseBitness(rawIdentifier),
                    parseEndianness(rawIdentifier),
                    parseVersion(rawIdentifier),
                    parseOsAbi(rawIdentifier),
                    parseElfOsAbiVersion(rawIdentifier)
            );
        } catch (IllegalArgumentException e) {
            throw new ElfParsingException("Invalid Elf Header Identifier: " + e, e);
        }
    }

    private ByteBuffer readIdentifierFromFile(FileChannel elfFile) throws ElfParsingException {
        ByteBuffer identifierBuffer = ByteBuffer.allocate(ELF_HEADER_IDENTIFIER_SIZE);
        int bytesRead;
        try {
            bytesRead = elfFile.read(identifierBuffer, 0);
        } catch (IOException e) {
            throw new ElfParsingException(e);
        }
        if (bytesRead != ELF_HEADER_IDENTIFIER_SIZE) {
            throw new ElfParsingException("Elf file does not contain enough bytes for a header identifier");
        }
        return identifierBuffer;
    }

    private ElfMagic parseMagic(ByteBuffer rawIdentifier) {
        final byte[] rawMagic = new byte[ElfMagic.MAGIC_SIZE];
        rawIdentifier.get(ELF_MAGIC_INDEX, rawMagic);
        return ElfMagic.fromBytes(rawMagic);
    }

    private ElfBitness parseBitness(ByteBuffer rawIdentifier) {
        return ElfBitness.fromBitnessCode(rawIdentifier.get(ELF_BITNESS_INDEX));
    }

    private ElfEndianness parseEndianness(ByteBuffer rawIdentifier) {
        return ElfEndianness.fromEndiannessCode(rawIdentifier.get(ELF_ENDIANNESS_INDEX));
    }

    private ElfHeaderVersion parseVersion(ByteBuffer rawIdentifier) {
        return ElfHeaderVersion.fromVersionCode(rawIdentifier.get(ELF_VERSION_INDEX));
    }

    private ElfOsAbi parseOsAbi(ByteBuffer rawIdentifier) {
        return ElfOsAbi.fromOsAbiCode(rawIdentifier.get(ELF_OS_ABI_INDEX));
    }

    private int parseElfOsAbiVersion(ByteBuffer rawIdentifier) {
        final byte rawOsAbiVersion = rawIdentifier.get(ELF_OS_ABI_VERSION_INDEX);
        return Byte.toUnsignedInt(rawOsAbiVersion);
    }
}
