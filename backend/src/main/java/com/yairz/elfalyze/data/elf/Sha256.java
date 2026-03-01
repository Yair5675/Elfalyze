package com.yairz.elfalyze.data.elf;

import org.jspecify.annotations.NonNull;

import java.util.HexFormat;

public record Sha256(byte[] bytes) {
    @Override
    public @NonNull String toString() {
        return HexFormat.of().formatHex(bytes);
    }
}
