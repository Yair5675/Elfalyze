package com.yairz.elfalyze.elf.models.header.machine;

import com.yairz.elfalyze.elf.types.ElfHalf;
import org.jetbrains.annotations.VisibleForTesting;

import java.util.Optional;

public final class ElfHeaderMachineArchitecture {
    private final ElfHalf rawMachine;

    @VisibleForTesting
    ElfHeaderMachineArchitecture(ElfHalf rawMachine) {
        this.rawMachine = rawMachine;
    }

    public static ElfHeaderMachineArchitecture fromHeaderMachineValue(ElfHalf rawMachine) {
        ElfMachineArchitectureLookup.validateMachineNumber(rawMachine.half());
        return new ElfHeaderMachineArchitecture(rawMachine);
    }

    public ElfHalf getRawMachine() {
        return rawMachine;
    }

    public boolean isRegistered() {
        return ElfMachineArchitectureLookup
                .getRegisteredMachinesLookup()
                .isDefinedMachineNumber(rawMachine.half());
    }

    public boolean isReserved() {
        return ElfMachineArchitectureLookup
                .getReservedMachinesLookup()
                .isReservedMachine(rawMachine.half());
    }

    /**
     * Returns the name of the machine architecture if it's registered, or for whom the machine is reserved.
     * If the machine is reserved there may not be a name, but if it is registered it is guaranteed to have a name.
     *
     * @return Name of the machine architecture, if it exists.
     */
    public Optional<String> getMachineName() {
        if (isRegistered()) {
            return ElfMachineArchitectureLookup
                    .getRegisteredMachinesLookup()
                    .getDefinedMachineName(rawMachine.half());
        }
        if (isReserved()) {
            return ElfMachineArchitectureLookup
                    .getReservedMachinesLookup()
                    .getReservedMachineOwner(rawMachine.half());
        }
        return Optional.empty();
    }

    @Override
    public String toString() {
        Optional<String> machineNameOpt = getMachineName();
        String machineName = isReserved() ?
                machineNameOpt.map(s -> "Reserved to " + s).orElse("Reserved (no owner)") :
                machineNameOpt.orElse("Invalid Machine");
        return String.format("%s (%d)", machineName, rawMachine.half());
    }
}
