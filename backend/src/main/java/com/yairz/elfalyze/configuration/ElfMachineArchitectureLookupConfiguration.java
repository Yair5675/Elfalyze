package com.yairz.elfalyze.configuration;

import com.yairz.elfalyze.elf.models.header.machine.registered.HardcodedRegisteredMachinesLookup;
import com.yairz.elfalyze.elf.models.header.machine.registered.RegisteredMachinesLookup;
import com.yairz.elfalyze.elf.models.header.machine.reserved.HardcodedReservedMachinesLookup;
import com.yairz.elfalyze.elf.models.header.machine.reserved.ReservedMachinesLookup;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElfMachineArchitectureLookupConfiguration {
    @Bean
    public RegisteredMachinesLookup registeredMachinesLookup() {
        // TODO Replace the hardcoded version in the future with some local file or smth
        return HardcodedRegisteredMachinesLookup.getInstance();
    }

    @Bean
    public ReservedMachinesLookup reservedMachinesLookup() {
        // TODO Replace the hardcoded version in the future with some local file or smth
        return HardcodedReservedMachinesLookup.getInstance();
    }
}
