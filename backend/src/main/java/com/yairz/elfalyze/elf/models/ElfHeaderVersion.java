package com.yairz.elfalyze.elf.models;

public enum ElfHeaderVersion {
    INVALID((byte) 0),
    CURRENT((byte) 1),
    ;
    private final byte versionCode;

    ElfHeaderVersion(byte versionCode) {
        this.versionCode = versionCode;
    }

    public static ElfHeaderVersion fromVersionCode(byte versionCode) {
        for (ElfHeaderVersion version : ElfHeaderVersion.values()) {
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
