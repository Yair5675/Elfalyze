package com.yairz.elfalyze.elf.models.header;

/**
 * Wrapper around the e_version field of the ELF header, do not be confused by the header identifier's EI_VERSION value
 * (although god knows I did).
 */
public enum ElfVersion {
    INVALID((byte) 0),
    CURRENT((byte) 1),
    ;
    private final byte versionCode;

    ElfVersion(byte versionCode) {
        this.versionCode = versionCode;
    }

    public static ElfVersion fromVersionCode(byte versionCode) {
        for (ElfVersion version : values()) {
            if (version.getVersionCode() == versionCode) {
                return version;
            }
        }
        throw new IllegalArgumentException("Unknown version code: " + versionCode);
    }

    public byte getVersionCode() {
        return versionCode;
    }
}
