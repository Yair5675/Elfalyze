package com.yairz.elfalyze.elf.parsers.header;


import com.yairz.elfalyze.elf.models.ElfBitness;
import com.yairz.elfalyze.elf.models.header.machine.ElfHeaderMachineArchitecture;
import com.yairz.elfalyze.elf.models.header.machine.registered.RegisteredMachinesLookup;
import com.yairz.elfalyze.elf.models.header.machine.reserved.ReservedMachinesLookup;
import com.yairz.elfalyze.elf.parsers.primitives.ElfPrimitivesParser;
import com.yairz.elfalyze.elf.types.ElfHalf;
import com.yairz.elfalyze.exceptions.elf.ElfParsingException;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.util.Optional;

@Component
public class ElfHeaderMachineArchitectureParser {
    private final RegisteredMachinesLookup registeredMachinesLookup;
    private final ReservedMachinesLookup reservedMachinesLookup;
    private final ElfPrimitivesParser primitivesParser;

    public ElfHeaderMachineArchitectureParser(RegisteredMachinesLookup registeredMachinesLookup,
                                              ReservedMachinesLookup reservedMachinesLookup,
                                              ElfPrimitivesParser primitivesParser) {
        this.registeredMachinesLookup = registeredMachinesLookup;
        this.reservedMachinesLookup = reservedMachinesLookup;
        this.primitivesParser = primitivesParser;
    }

    /**
     * Parses the ELF header's machine architecture field ({@link ElfHeaderMachineArchitecture e_machine}).
     *
     * @param rawMachineArchitecture A buffer containing the raw bytes found in the ELF file whose current position is
     *                               at the machine architecture.
     * @param bitness                The bitness of the ELF file.
     * @return The parsed machine architecture.
     * @throws ElfParsingException If there aren't enough bytes in the buffer to read the machine architecture, or if it
     *                             is an invalid architecture (neither registered nor reserved).
     */
    public ElfHeaderMachineArchitecture parseMachineArchitecture(ByteBuffer rawMachineArchitecture, ElfBitness bitness) throws ElfParsingException {
        ElfHalf rawMachine = primitivesParser.parsePrimitive(rawMachineArchitecture, ElfHalf.class, bitness);
        final short machineNumber = rawMachine.half();
        boolean isRegisteredMachine = isRegisteredMachine(machineNumber)
                .orElseThrow(() -> new ElfParsingException("Invalid machine architecture number: " + machineNumber));
        Optional<String> optionalMachineName = getOptionalMachineName(machineNumber, isRegisteredMachine);
        String machineName = isRegisteredMachine ?
                // First case's else is impossible since every registered machine is guaranteed to have a name
                optionalMachineName.orElse("Nameless registered machine") :
                optionalMachineName.orElse("Reserved (no owner)");
        return new ElfHeaderMachineArchitecture(rawMachine, isRegisteredMachine, machineName);
    }

    private Optional<Boolean> isRegisteredMachine(short machineNumber) {
        if (registeredMachinesLookup.isDefinedMachineNumber(machineNumber)) return Optional.of(true);
        if (reservedMachinesLookup.isReservedMachine(machineNumber)) return Optional.of(false);
        return Optional.empty();
    }

    private Optional<String> getOptionalMachineName(short machineNumber, boolean isRegisteredMachine) {
        if (isRegisteredMachine) {
            return registeredMachinesLookup.getDefinedMachineName(machineNumber);
        }
        return reservedMachinesLookup.getReservedMachineOwner(machineNumber);
    }
}
