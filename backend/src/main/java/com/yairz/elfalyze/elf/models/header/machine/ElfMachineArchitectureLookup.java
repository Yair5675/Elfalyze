package com.yairz.elfalyze.elf.models.header.machine;

import com.yairz.elfalyze.elf.models.header.machine.registered.RegisteredMachinesLookup;
import com.yairz.elfalyze.elf.models.header.machine.reserved.ReservedMachinesLookup;
import org.springframework.stereotype.Component;

@Component
public final class ElfMachineArchitectureLookup {
    private static RegisteredMachinesLookup registeredMachinesLookup;
    private static ReservedMachinesLookup reservedMachinesLookup;

    public ElfMachineArchitectureLookup(
            RegisteredMachinesLookup registeredMachinesLookup, ReservedMachinesLookup reservedMachinesLookup) {
        ElfMachineArchitectureLookup.registeredMachinesLookup = registeredMachinesLookup;
        ElfMachineArchitectureLookup.reservedMachinesLookup = reservedMachinesLookup;
    }

    public static void validateMachineNumber(short machineNumber) {
        if (registeredMachinesLookup.isDefinedMachineNumber(machineNumber)) return;
        if (reservedMachinesLookup.isReservedMachine(machineNumber)) return;
        throw new IllegalArgumentException("Invalid machine architecture number: " + machineNumber);
    }

    public static RegisteredMachinesLookup getRegisteredMachinesLookup() {
        return registeredMachinesLookup;
    }

    public static ReservedMachinesLookup getReservedMachinesLookup() {
        return reservedMachinesLookup;
    }
}
