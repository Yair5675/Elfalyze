package com.yairz.elfalyze.elf.models.header.machine.reserved;

import java.util.Optional;

public interface ReservedMachinesLookup {
    String ARM_OWNER = "ARM";
    String INTEL_OWNER = "Intel";
    String TACHYUM_PROCESSOR_OWNER = "Tachyum processor";

    /**
     * Checks if the given machine number is marked as a reserved machine.
     *
     * @param machineNumber The raw number in the {@code e_machine} field of the header.
     * @return True if the given machine number is reserved for future use.
     * @apiNote If the function returns false it can be either because the machine number is a defined
     * machine, OR it is a non-registered/invalid machine.
     */
    boolean isReservedMachine(short machineNumber);

    /**
     * Given a reserved machine number, the function returns the name for whom it is reserved.
     * This value may not exist if one of the following is true:
     * <ol>
     *     <li> The given machine number is not a reserved machine.
     *     <li> The machine number IS reserved, but not to a particular individual/group.
     *     <li> The given machine number is not a valid e_machine value.
     * </ol>
     * If the value doesn't exist, an empty optional is returned.
     *
     * @param machineNumber The raw number in the {@code e_machine} field of the header.
     * @return The name for whom the machine is reserved, or an empty optional if the value doesn't exist.
     */
    Optional<String> getReservedMachineOwner(short machineNumber);
}
