package com.yairz.elfalyze.elf.models.header;

import com.yairz.elfalyze.elf.models.header.machine.ElfHeaderMachineArchitecture;
import com.yairz.elfalyze.elf.models.sections.ElfSectionIndex;
import com.yairz.elfalyze.elf.types.ElfAddress;
import com.yairz.elfalyze.elf.types.ElfHalf;
import com.yairz.elfalyze.elf.types.ElfOffset;
import com.yairz.elfalyze.elf.types.ElfWord;

/**
 * Data representation of the {@code Elf32_Ehdr} and {@code Elf64_Ehdr} structs defined by the ELF format.
 * It does not include the header identifier though, for that see
 * {@link com.yairz.elfalyze.elf.models.ElfHeaderIdentifier ElfHeaderIdentifier}.
 *
 * @param type                          e_type      -   This member identifies the object file type.
 * @param machine                       e_machine   -   This member’s value specifies the required architecture for an
 *                                                      individual file.
 * @param version                       e_version   -   This member identifies the object file version.
 * @param entryAddress                  e_entry     -   This member gives the virtual address to which the system first
 *                                                      transfers control, thus starting the process. If the file has no
 *                                                      associated entry point, this member holds zero.
 * @param programHeaderTableOffset      e_phoff     -   This member holds the program header table’s file offset in
 *                                                      bytes. If the file has no program header table, this member
 *                                                      holds zero.
 * @param sectionHeaderTableOffset      e_shoff     -   This member holds the section header table’s file offset in
 *                                                      bytes. If the file has no section header table, this member
 *                                                      holds zero.
 * @param processorSpecificFlags        e_flags     -   This member holds processor-specific flags associated with the
 *                                                      file.
 * @param headerSizeBytes               e_ehsize    -   This member holds the ELF header’s size in bytes (including
 *                                                      identifier).
 * @param programHeaderSizeBytes        e_phentsize -   This member holds the size in bytes of one entry in the file’s
 *                                                      program header table; all entries are the same size.
 * @param programHeadersCount           e_phnum     -   This member holds the number of entries in the program header
 *                                                      table. Thus, the product of
 *                                                      {@link ElfHeader#programHeaderSizeBytes e_phentsize} and
 *                                                      {@link ElfHeader#programHeadersCount e_phnum} gives the
 *                                                      table’s size in bytes. If a file has no program header table,
 *                                                      this member holds the value zero.
 * @param sectionHeaderSizeBytes        e_shentsize -   This member holds a section header’s size in bytes. A section
 *                                                      header is one entry in the section header table; all entries are
 *                                                      the same size.
 * @param sectionHeadersCount           e_shnum     -   This member holds the number of entries in the section header
 *                                                      table. Thus, the product of
 *                                                      {@link ElfHeader#sectionHeaderSizeBytes e_shentsize} and
 *                                                      {@link ElfHeader#sectionHeadersCount e_shnum} gives the section
 *                                                      header table’s size in bytes. If a file has no section header
 *                                                      table, this member holds the value zero.<br>
 *                                                      If the number of sections is greater than or equal to
 *                                                      {@link ElfSectionIndex#FIRST_RESERVED_SECTION_INDEX SHN_LORESERVE},
 *                                                      this member has the value zero and the actual number of section
 *                                                      header table entries is contained in the sh_size field of the
 *                                                      section header at index 0 (Otherwise, the sh_size
 *                                                      member of the initial entry contains 0).
 * @param sectionNameStringTableIndex   e_shstrndx  -   This member holds the section header table index of the entry
 *                                                      associated with the section name string table.<br>
 *                                                      If the file has no section name string table, this member holds
 *                                                      the value {@link ElfSectionIndex#UNDEFINED_SECTION_INDEX SHN_UNDEF}.<br>
 *                                                      If the section name string table section index is greater than
 *                                                      or equal to
 *                                                      {@link ElfSectionIndex#FIRST_RESERVED_SECTION_INDEX SHN_LORESERVE},
 *                                                      this member has the value
 *                                                      {@link ElfSectionIndex#ESCAPE_SECTION_INDEX SHN_XINDEX}
 *                                                      and the actual index of the section name string table section is
 *                                                      contained in the sh_link field of the section header at index 0
 *                                                      (Otherwise, the sh_link member of the initial entry contains 0).
 * @see com.yairz.elfalyze.elf.models.ElfHeaderIdentifier
 * @see ElfHeaderType
 * @see ElfVersion
 * @see ElfHeaderMachineArchitecture
 */
public record ElfHeader(
        ElfHeaderType type,
        ElfHeaderMachineArchitecture machine,
        ElfVersion version,
        ElfAddress entryAddress,
        ElfOffset programHeaderTableOffset,
        ElfOffset sectionHeaderTableOffset,
        ElfWord processorSpecificFlags,
        ElfHalf headerSizeBytes,
        ElfHalf programHeaderSizeBytes,
        ElfHalf programHeadersCount,
        ElfHalf sectionHeaderSizeBytes,
        ElfHalf sectionHeadersCount,
        ElfSectionIndex sectionNameStringTableIndex
) {
}
