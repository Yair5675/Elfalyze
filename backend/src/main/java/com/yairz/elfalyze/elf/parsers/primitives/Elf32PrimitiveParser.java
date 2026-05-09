package com.yairz.elfalyze.elf.parsers.primitives;

import com.yairz.elfalyze.elf.types.Elf32Primitive;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;

import java.nio.ByteBuffer;

/**
 * Interface responsible for parsing primitive 32-bit data types the ELF spec declared.
 *
 * @param <Primitive32> The primitive which will be parsed by the class.
 */
public interface Elf32PrimitiveParser<Primitive32 extends Elf32Primitive> {
    /**
     * Parses the primitive from a byte buffer. The primitive will be read from the current position of the byte buffer,
     * and the caller should assume that the buffer's position will change following the operation.
     * Moreover, the byte buffer's endianness will be the deciding factor when setting an elf primitive's value. Callers
     * should set the endianness in advance.
     *
     * @param rawBytes Byte buffer which contains the bytes for the elf primitive. Should have enough bytes remaining to
     *                 read {@link Primitive32} from.
     * @return The parsed {@link Primitive32}.
     * @throws ElfParsingException If parsing the primitive failed due to some issue with the given bytes (like not
     *                             enough bytes in the buffer for example).
     */
    Primitive32 parse32(ByteBuffer rawBytes) throws ElfParsingException;
}
