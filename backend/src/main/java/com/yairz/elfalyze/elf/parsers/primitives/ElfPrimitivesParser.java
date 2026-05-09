package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.models.ElfBitness;
import com.yairz.elfalyze.elf.types.*;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.List;

@Component
public class ElfPrimitivesParser {

    private final List<PrimitiveParserMapping32<? extends Elf32Primitive>> parserMappings32;
    private final List<PrimitiveParserMapping64<? extends Elf64Primitive>> parserMappings64;

    public ElfPrimitivesParser(
            // All 32-bit architecture data types:
            Elf32PrimitiveParser<ElfAddress> address32Parser,
            Elf32PrimitiveParser<ElfHalf> half32Parser,
            Elf32PrimitiveParser<ElfOffset> offset32Parser,
            Elf32PrimitiveParser<ElfSignedWord> signedWord32Parser,
            Elf32PrimitiveParser<ElfWord> word32Parser,
            // Now all 64-bit architecture data types:
            Elf64PrimitiveParser<ElfAddress> address64Parser,
            Elf64PrimitiveParser<ElfHalf> half64Parser,
            Elf64PrimitiveParser<ElfOffset> offset64Parser,
            Elf64PrimitiveParser<ElfSignedWord> signedWord64Parser,
            Elf64PrimitiveParser<ElfSignedXWord> signedXWord64Parser,
            Elf64PrimitiveParser<ElfWord> word64Parser,
            Elf64PrimitiveParser<ElfXWord> xWord64Parser
    ) {
        // Dirty work but due to type erasure we can't access a generic type parameter's class, so we have
        // to manually write it here:
        parserMappings32 = List.of(
                new PrimitiveParserMapping32<>(ElfAddress.class, address32Parser),
                new PrimitiveParserMapping32<>(ElfHalf.class, half32Parser),
                new PrimitiveParserMapping32<>(ElfOffset.class, offset32Parser),
                new PrimitiveParserMapping32<>(ElfSignedWord.class, signedWord32Parser),
                new PrimitiveParserMapping32<>(ElfWord.class, word32Parser)
        );

        // Here as well :(
        parserMappings64 = List.of(
                new PrimitiveParserMapping64<>(ElfAddress.class, address64Parser),
                new PrimitiveParserMapping64<>(ElfHalf.class, half64Parser),
                new PrimitiveParserMapping64<>(ElfOffset.class, offset64Parser),
                new PrimitiveParserMapping64<>(ElfSignedWord.class, signedWord64Parser),
                new PrimitiveParserMapping64<>(ElfSignedXWord.class, signedXWord64Parser),
                new PrimitiveParserMapping64<>(ElfWord.class, word64Parser),
                new PrimitiveParserMapping64<>(ElfXWord.class, xWord64Parser)
        );
    }

    /**
     * Parses an ELF primitive flexibly.
     *
     * @param rawPrimitive         The raw bytes of the primitive. The endianness of the byte buffer should be set BEFORE
     *                             passing it to the function. Moreover, the function WILL alter the buffer's position, and will
     *                             attempt to parse the primitive from the current position. If there aren't enough bytes for
     *                             the primitive, {@link ElfParsingException} will be thrown.
     * @param targetPrimitiveClass The primitive type which will be parsed. Note that if this type conflicts with the
     *                             given bitness value (i.e - a class which only inherits from {@link Elf64Primitive}
     *                             and {@link ElfBitness#BITNESS_32} are given) {@link ElfParsingException} will be thrown.
     * @param bitness              The bitness of the ELF primitive. Required because some classes can be parsed in both 32 and 64
     *                             modes.
     * @param <P>                  The type of primitive being parsed.
     * @return The parsed ELF primitive.
     * @throws ElfParsingException If parsing failed for any reason.
     */
    @SuppressWarnings("unchecked")
    public <P extends ElfPrimitive> P parsePrimitive(ByteBuffer rawPrimitive, Class<P> targetPrimitiveClass, ElfBitness bitness) throws ElfParsingException {
        switch (bitness) {
            case BITNESS_32 -> {
                if (Elf32Primitive.class.isAssignableFrom(targetPrimitiveClass)) {
                    return (P) parse32(rawPrimitive, (Class<? extends Elf32Primitive>) targetPrimitiveClass);
                }
            }
            case BITNESS_64 -> {
                if (Elf64Primitive.class.isAssignableFrom(targetPrimitiveClass)) {
                    return (P) parse64(rawPrimitive, (Class<? extends Elf64Primitive>) targetPrimitiveClass);
                }
            }
            default -> throw new ElfParsingException("Unsupported bitness: " + bitness);
        }
        // If we reached this point, caller lied and gave a 64-only primitive with 32 bitness
        throw new ElfParsingException("Given primitive class doesn't support the given bitness");
    }

    private <P32 extends Elf32Primitive> P32 parse32(ByteBuffer rawPrimitive, Class<P32> primitiveClass) {
        return getParser32(primitiveClass).parse32(rawPrimitive);
    }

    private <P64 extends Elf64Primitive> P64 parse64(ByteBuffer rawPrimitive, Class<P64> primitiveClass) {
        return getParser64(primitiveClass).parse64(rawPrimitive);
    }

    @SuppressWarnings("unchecked")
    private <P32 extends Elf32Primitive> Elf32PrimitiveParser<P32> getParser32(Class<P32> primitiveClass) {
        return (Elf32PrimitiveParser<P32>) parserMappings32
                .stream()
                .filter(mapping32 -> mapping32.primitiveClass.equals(primitiveClass))
                .findFirst()
                .orElseThrow(() -> new ElfParsingException("Missing parsing class for " + primitiveClass.getName()))
                .parser;
    }

    @SuppressWarnings("unchecked")
    private <P64 extends Elf64Primitive> Elf64PrimitiveParser<P64> getParser64(Class<P64> primitiveClass) {
        return (Elf64PrimitiveParser<P64>) parserMappings64
                .stream()
                .filter(mapping64 -> mapping64.primitiveClass.equals(primitiveClass))
                .findFirst()
                .orElseThrow(() -> new ElfParsingException("Missing parsing class for " + primitiveClass.getName()))
                .parser;
    }

    private static class PrimitiveParserMapping32<P32 extends Elf32Primitive> {
        public Class<P32> primitiveClass;
        public Elf32PrimitiveParser<P32> parser;

        public PrimitiveParserMapping32(Class<P32> primitiveClass, Elf32PrimitiveParser<P32> parser) {
            this.primitiveClass = primitiveClass;
            this.parser = parser;
        }
    }

    private static class PrimitiveParserMapping64<P64 extends Elf64Primitive> {
        public Class<P64> primitiveClass;
        public Elf64PrimitiveParser<P64> parser;

        public PrimitiveParserMapping64(Class<P64> primitiveClass, Elf64PrimitiveParser<P64> parser) {
            this.primitiveClass = primitiveClass;
            this.parser = parser;
        }
    }
}
