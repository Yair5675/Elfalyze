package com.yairz.elfalyze.elf.models.header.machine;

import com.yairz.elfalyze.elf.types.ElfHalf;
import org.jspecify.annotations.NonNull;

public record ElfHeaderMachineArchitecture(ElfHalf rawMachine, boolean isRegisteredMachine, String machineName) {
    public boolean isReservedMachine() {
        return !isRegisteredMachine;
    }

    @Override
    public @NonNull String toString() {
        return String.format("%s (%d)", machineName, rawMachine.half());
    }
}
