package com.yairz.elfalyze.elf.models.header.machine.registered;

import org.jetbrains.annotations.VisibleForTesting;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class HardcodedRegisteredMachinesLookup implements RegisteredMachinesLookup {
    private static final int HARDCODED_REGISTERED_MACHINES_COUNT = 202;

    private static HardcodedRegisteredMachinesLookup instance;

    private final Map<Short, String> machineNumberToName;

    @VisibleForTesting
    HardcodedRegisteredMachinesLookup(Map<Short, String> machineNumberToName) {
        this.machineNumberToName = machineNumberToName;
    }

    public static HardcodedRegisteredMachinesLookup getInstance() {
        if (instance == null) {
            instance = new HardcodedRegisteredMachinesLookup(getHardcodedMachinesMappings());
        }
        return instance;
    }

    private static Map<Short, String> getHardcodedMachinesMappings() {
        Map<Short, String> machineNumberToName = new HashMap<>(HARDCODED_REGISTERED_MACHINES_COUNT);
        // Forgive me ༼ つ ◕_◕ ༽つ
        {
            machineNumberToName.put((short) 0, "No machine");
            machineNumberToName.put((short) 1, "AT&T WE 32100");
            machineNumberToName.put((short) 2, "SPARC");
            machineNumberToName.put((short) 3, "Intel 80386");
            machineNumberToName.put((short) 4, "Motorola 68000");
            machineNumberToName.put((short) 5, "Motorola 88000");
            machineNumberToName.put((short) 6, "Intel MCU");
            machineNumberToName.put((short) 7, "Intel 80860");
            machineNumberToName.put((short) 8, "MIPS I Architecture");
            machineNumberToName.put((short) 9, "IBM System/370 Processor");
            machineNumberToName.put((short) 10, "MIPS RS3000 Little-endian");
            machineNumberToName.put((short) 15, "Hewlett-Packard PA-RISC");
            machineNumberToName.put((short) 17, "Fujitsu VPP500");
            machineNumberToName.put((short) 18, "Enhanced instruction set SPARC");
            machineNumberToName.put((short) 19, "Intel 80960");
            machineNumberToName.put((short) 20, "PowerPC");
            machineNumberToName.put((short) 21, "64-bit PowerPC");
            machineNumberToName.put((short) 22, "IBM System/390 Processor");
            machineNumberToName.put((short) 23, "IBM SPU/SPC");
            machineNumberToName.put((short) 36, "NEC V800");
            machineNumberToName.put((short) 37, "Fujitsu FR20");
            machineNumberToName.put((short) 38, "TRW RH-32");
            machineNumberToName.put((short) 39, "Motorola RCE");
            machineNumberToName.put((short) 40, "ARM 32-bit architecture (AARCH32)");
            machineNumberToName.put((short) 41, "Digital Alpha");
            machineNumberToName.put((short) 42, "Hitachi SH");
            machineNumberToName.put((short) 43, "SPARC Version 9");
            machineNumberToName.put((short) 44, "Siemens TriCore embedded processor");
            machineNumberToName.put((short) 45, "Argonaut RISC Core, Argonaut Technologies Inc.");
            machineNumberToName.put((short) 46, "Hitachi H8/300");
            machineNumberToName.put((short) 47, "Hitachi H8/300H");
            machineNumberToName.put((short) 48, "Hitachi H8S");
            machineNumberToName.put((short) 49, "Hitachi H8/500");
            machineNumberToName.put((short) 50, "Intel IA-64 processor architecture");
            machineNumberToName.put((short) 51, "Stanford MIPS-X");
            machineNumberToName.put((short) 52, "Motorola ColdFire");
            machineNumberToName.put((short) 53, "Motorola M68HC12");
            machineNumberToName.put((short) 54, "Fujitsu MMA Multimedia Accelerator");
            machineNumberToName.put((short) 55, "Siemens PCP");
            machineNumberToName.put((short) 56, "Sony nCPU embedded RISC processor");
            machineNumberToName.put((short) 57, "Denso NDR1 microprocessor");
            machineNumberToName.put((short) 58, "Motorola Star*Core processor");
            machineNumberToName.put((short) 59, "Toyota ME16 processor");
            machineNumberToName.put((short) 60, "STMicroelectronics ST100 processor");
            machineNumberToName.put((short) 61, "Advanced Logic Corp. TinyJ embedded processor family");
            machineNumberToName.put((short) 62, "AMD x86-64 architecture");
            machineNumberToName.put((short) 63, "Sony DSP Processor");
            machineNumberToName.put((short) 64, "Digital Equipment Corp. PDP-10");
            machineNumberToName.put((short) 65, "Digital Equipment Corp. PDP-11");
            machineNumberToName.put((short) 66, "Siemens FX66 microcontroller");
            machineNumberToName.put((short) 67, "STMicroelectronics ST9+ 8/16 bit microcontroller");
            machineNumberToName.put((short) 68, "STMicroelectronics ST7 8-bit microcontroller");
            machineNumberToName.put((short) 69, "Motorola MC68HC16 Microcontroller");
            machineNumberToName.put((short) 70, "Motorola MC68HC11 Microcontroller");
            machineNumberToName.put((short) 71, "Motorola MC68HC08 Microcontroller");
            machineNumberToName.put((short) 72, "Motorola MC68HC05 Microcontroller");
            machineNumberToName.put((short) 73, "Silicon Graphics SVx");
            machineNumberToName.put((short) 74, "STMicroelectronics ST19 8-bit microcontroller");
            machineNumberToName.put((short) 75, "Digital VAX");
            machineNumberToName.put((short) 76, "Axis Communications 32-bit embedded processor");
            machineNumberToName.put((short) 77, "Infineon Technologies 32-bit embedded processor");
            machineNumberToName.put((short) 78, "Element 14 64-bit DSP Processor");
            machineNumberToName.put((short) 79, "LSI Logic 16-bit DSP Processor");
            machineNumberToName.put((short) 80, "Donald Knuth’s educational 64-bit processor");
            machineNumberToName.put((short) 81, "Harvard University machine-independent object files");
            machineNumberToName.put((short) 82, "SiTera Prism");
            machineNumberToName.put((short) 83, "Atmel AVR 8-bit microcontroller");
            machineNumberToName.put((short) 84, "Fujitsu FR30");
            machineNumberToName.put((short) 85, "Mitsubishi D10V");
            machineNumberToName.put((short) 86, "Mitsubishi D30V");
            machineNumberToName.put((short) 87, "NEC v850");
            machineNumberToName.put((short) 88, "Mitsubishi M32R");
            machineNumberToName.put((short) 89, "Matsushita MN10300");
            machineNumberToName.put((short) 90, "Matsushita MN10200");
            machineNumberToName.put((short) 91, "picoJava");
            machineNumberToName.put((short) 92, "OpenRISC 32-bit embedded processor");
            machineNumberToName.put((short) 93, "ARC International ARCompact processor");
            machineNumberToName.put((short) 94, "Tensilica Xtensa Architecture");
            machineNumberToName.put((short) 95, "Alphamosaic VideoCore processor");
            machineNumberToName.put((short) 96, "Thompson Multimedia General Purpose Processor");
            machineNumberToName.put((short) 97, "National Semiconductor 32000 series");
            machineNumberToName.put((short) 98, "Tenor Network TPC processor");
            machineNumberToName.put((short) 99, "Trebia SNP 1000 processor");
            machineNumberToName.put((short) 100, "STMicroelectronics (www.st.com) ST200 microcontroller");
            machineNumberToName.put((short) 101, "Ubicom IP2xxx microcontroller family");
            machineNumberToName.put((short) 102, "MAX Processor");
            machineNumberToName.put((short) 103, "National Semiconductor CompactRISC microprocessor");
            machineNumberToName.put((short) 104, "Fujitsu F2MC16");
            machineNumberToName.put((short) 105, "Texas Instruments embedded microcontroller msp430");
            machineNumberToName.put((short) 106, "Analog Devices Blackfin (DSP) processor");
            machineNumberToName.put((short) 107, "S1C33 Family of Seiko Epson processors");
            machineNumberToName.put((short) 108, "Sharp embedded microprocessor");
            machineNumberToName.put((short) 109, "Arca RISC Microprocessor");
            machineNumberToName.put((short) 110, "Microprocessor series from PKU-Unity Ltd. and MPRC of Peking University");
            machineNumberToName.put((short) 111, "eXcess: 16/32/64-bit configurable embedded CPU");
            machineNumberToName.put((short) 112, "Icera Semiconductor Inc. Deep Execution Processor");
            machineNumberToName.put((short) 113, "Altera Nios II soft-core processor");
            machineNumberToName.put((short) 114, "National Semiconductor CompactRISC CRX microprocessor");
            machineNumberToName.put((short) 115, "Motorola XGATE embedded processor");
            machineNumberToName.put((short) 116, "Infineon C16x/XC16x processor");
            machineNumberToName.put((short) 117, "Renesas M16C series microprocessors");
            machineNumberToName.put((short) 118, "Microchip Technology dsPIC30F Digital Signal Controller");
            machineNumberToName.put((short) 119, "Freescale Communication Engine RISC core");
            machineNumberToName.put((short) 120, "Renesas M32C series microprocessors");
            machineNumberToName.put((short) 131, "Altium TSK3000 core");
            machineNumberToName.put((short) 132, "Freescale RS08 embedded processor");
            machineNumberToName.put((short) 133, "Analog Devices SHARC family of 32-bit DSP processors");
            machineNumberToName.put((short) 134, "Cyan Technology eCOG2 microprocessor");
            machineNumberToName.put((short) 135, "Sunplus S+core7 RISC processor");
            machineNumberToName.put((short) 136, "New Japan Radio (NJR) 24-bit DSP Processor");
            machineNumberToName.put((short) 137, "Broadcom VideoCore III processor");
            machineNumberToName.put((short) 138, "RISC processor for Lattice FPGA architecture");
            machineNumberToName.put((short) 139, "Seiko Epson C17 family");
            machineNumberToName.put((short) 140, "The Texas Instruments TMS320C6000 DSP family");
            machineNumberToName.put((short) 141, "The Texas Instruments TMS320C2000 DSP family");
            machineNumberToName.put((short) 142, "The Texas Instruments TMS320C55x DSP family");
            machineNumberToName.put((short) 143, "Texas Instruments Application Specific RISC Processor, 32bit fetch");
            machineNumberToName.put((short) 144, "Texas Instruments Programmable Realtime Unit");
            machineNumberToName.put((short) 160, "STMicroelectronics 64bit VLIW Data Signal Processor");
            machineNumberToName.put((short) 161, "Cypress M8C microprocessor");
            machineNumberToName.put((short) 162, "Renesas R32C series microprocessors");
            machineNumberToName.put((short) 163, "NXP Semiconductors TriMedia architecture family");
            machineNumberToName.put((short) 164, "QUALCOMM DSP6 Processor");
            machineNumberToName.put((short) 165, "Intel 8051 and variants");
            machineNumberToName.put((short) 166, "STMicroelectronics STxP7x family of configurable and extensible RISC processors");
            machineNumberToName.put((short) 167, "Andes Technology compact code size embedded RISC processor family");
            machineNumberToName.put((short) 168, "Cyan Technology eCOG1X family");
            machineNumberToName.put((short) 169, "Dallas Semiconductor MAXQ30 Core Micro-controllers");
            machineNumberToName.put((short) 170, "New Japan Radio (NJR) 16-bit DSP Processor");
            machineNumberToName.put((short) 171, "M2000 Reconfigurable RISC Microprocessor");
            machineNumberToName.put((short) 172, "Cray Inc. NV2 vector architecture");
            machineNumberToName.put((short) 173, "Renesas RX family");
            machineNumberToName.put((short) 174, "Imagination Technologies META processor architecture");
            machineNumberToName.put((short) 175, "MCST Elbrus general purpose hardware architecture");
            machineNumberToName.put((short) 176, "Cyan Technology eCOG16 family");
            machineNumberToName.put((short) 177, "National Semiconductor CompactRISC CR16 16-bit microprocessor");
            machineNumberToName.put((short) 178, "Freescale Extended Time Processing Unit");
            machineNumberToName.put((short) 179, "Infineon Technologies SLE9X core");
            machineNumberToName.put((short) 180, "Intel L10M");
            machineNumberToName.put((short) 181, "Intel K10M");
            machineNumberToName.put((short) 183, "ARM 64-bit architecture (AARCH64)");
            machineNumberToName.put((short) 185, "Atmel Corporation 32-bit microprocessor family");
            machineNumberToName.put((short) 186, "STMicroeletronics STM8 8-bit microcontroller");
            machineNumberToName.put((short) 187, "Tilera TILE64 multicore architecture family");
            machineNumberToName.put((short) 188, "Tilera TILEPro multicore architecture family");
            machineNumberToName.put((short) 189, "Xilinx MicroBlaze 32-bit RISC soft processor core");
            machineNumberToName.put((short) 190, "NVIDIA CUDA architecture");
            machineNumberToName.put((short) 191, "Tilera TILE-Gx multicore architecture family");
            machineNumberToName.put((short) 192, "CloudShield architecture family");
            machineNumberToName.put((short) 193, "KIPO-KAIST Core-A 1st generation processor family");
            machineNumberToName.put((short) 194, "KIPO-KAIST Core-A 2nd generation processor family");
            machineNumberToName.put((short) 195, "Synopsys ARCompact V2");
            machineNumberToName.put((short) 196, "Open8 8-bit RISC soft processor core");
            machineNumberToName.put((short) 197, "Renesas RL78 family");
            machineNumberToName.put((short) 198, "Broadcom VideoCore V processor");
            machineNumberToName.put((short) 199, "Renesas 78KOR family");
            machineNumberToName.put((short) 200, "Freescale 56800EX Digital Signal Controller (DSC)");
            machineNumberToName.put((short) 201, "Beyond BA1 CPU architecture");
            machineNumberToName.put((short) 202, "Beyond BA2 CPU architecture");
            machineNumberToName.put((short) 203, "XMOS xCORE processor family");
            machineNumberToName.put((short) 204, "Microchip 8-bit PIC(r) family");
            machineNumberToName.put((short) 210, "KM211 KM32 32-bit processor");
            machineNumberToName.put((short) 211, "KM211 KMX32 32-bit processor");
            machineNumberToName.put((short) 212, "KM211 KMX16 16-bit processor");
            machineNumberToName.put((short) 213, "KM211 KMX8 8-bit processor");
            machineNumberToName.put((short) 214, "KM211 KVARC processor");
            machineNumberToName.put((short) 215, "Paneve CDP architecture family");
            machineNumberToName.put((short) 216, "Cognitive Smart Memory Processor");
            machineNumberToName.put((short) 217, "Bluechip Systems CoolEngine");
            machineNumberToName.put((short) 218, "Nanoradio Optimized RISC");
            machineNumberToName.put((short) 219, "CSR Kalimba architecture family");
            machineNumberToName.put((short) 220, "Zilog Z80");
            machineNumberToName.put((short) 221, "Controls and Data Services VISIUMcore processor");
            machineNumberToName.put((short) 222, "FTDI Chip FT32 high performance 32-bit RISC architecture");
            machineNumberToName.put((short) 223, "Moxie processor family");
            machineNumberToName.put((short) 224, "AMD GPU architecture");
            machineNumberToName.put((short) 243, "RISC-V");
            machineNumberToName.put((short) 244, "Lanai processor");
            machineNumberToName.put((short) 245, "CEVA Processor Architecture Family");
            machineNumberToName.put((short) 246, "CEVA X2 Processor Family");
            machineNumberToName.put((short) 247, "Linux BPF – in-kernel virtual machine");
            machineNumberToName.put((short) 248, "Graphcore Intelligent Processing Unit");
            machineNumberToName.put((short) 249, "Imagination Technologies");
            machineNumberToName.put((short) 250, "Netronome Flow Processor (NFP)");
            machineNumberToName.put((short) 251, "NEC Vector Engine");
            machineNumberToName.put((short) 252, "C-SKY processor family");
            machineNumberToName.put((short) 253, "Synopsys ARCv2.3 64-bit");
            machineNumberToName.put((short) 254, "MOS Technology MCS 6502 processor");
            machineNumberToName.put((short) 255, "Synopsys ARCv2.3 32-bit");
            machineNumberToName.put((short) 256, "Kalray VLIW core of the MPPA processor family");
            machineNumberToName.put((short) 257, "WDC 65816/65C816");
            machineNumberToName.put((short) 258, "Loongson Loongarch");
            machineNumberToName.put((short) 259, "ChipON KungFu32");
            machineNumberToName.put((short) 260, "LAPIS nX-U16/U8");
            machineNumberToName.put((short) 262, "NXP 56800EF Digital Signal Controller (DSC)");
            machineNumberToName.put((short) 263, "Solana Bytecode Format");
            machineNumberToName.put((short) 264, "AMD/Xilinx AIEngine architecture");
            machineNumberToName.put((short) 265, "SiMa MLA");
            machineNumberToName.put((short) 266, "Cambricon BANG");
            machineNumberToName.put((short) 267, "Loongson LoongGPU");
            machineNumberToName.put((short) 268, "Wuxi Institute of Advanced Technology SW64");
            machineNumberToName.put((short) 269, "AMD/Xilinx AIEngine ctrlcode");
        }
        return Collections.unmodifiableMap(machineNumberToName);
    }

    @Override
    public boolean isDefinedMachineNumber(short machineNumber) {
        return machineNumberToName.containsKey(machineNumber);
    }

    @Override
    public Optional<String> getDefinedMachineName(short machineNumber) {
        return Optional.ofNullable(machineNumberToName.get(machineNumber));
    }
}
