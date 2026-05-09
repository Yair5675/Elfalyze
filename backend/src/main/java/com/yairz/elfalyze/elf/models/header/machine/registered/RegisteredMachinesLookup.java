package com.yairz.elfalyze.elf.models.header.machine.registered;

import java.util.Optional;

public interface RegisteredMachinesLookup {
    boolean isDefinedMachineNumber(short machineNumber);

    Optional<String> getDefinedMachineName(short machineNumber);
}
