package com.yairz.elfalyze.elf.models.header.machine.reserved;

import org.jetbrains.annotations.VisibleForTesting;

import java.util.List;
import java.util.Optional;

public final class HardcodedReservedMachinesLookup implements ReservedMachinesLookup {
    private static HardcodedReservedMachinesLookup instance;
    private final List<InclusiveReservedMachineRange> sortedReservedMachinesRanges;

    @VisibleForTesting
    HardcodedReservedMachinesLookup(List<InclusiveReservedMachineRange> sortedReservedMachinesRanges) {
        this.sortedReservedMachinesRanges = sortedReservedMachinesRanges;
    }

    public static HardcodedReservedMachinesLookup getInstance() {
        if (instance == null) {
            instance = new HardcodedReservedMachinesLookup(getHardcodedRanges());
        }
        return instance;
    }

    private static List<InclusiveReservedMachineRange> getHardcodedRanges() {
        return List.of(
                new InclusiveReservedMachineRange(11, 14),
                new InclusiveReservedMachineRange(16),
                new InclusiveReservedMachineRange(24, 35),
                new InclusiveReservedMachineRange(121, 130),
                new InclusiveReservedMachineRange(145, 159),
                new InclusiveReservedMachineRange(182, ReservedMachinesLookup.INTEL_OWNER),
                new InclusiveReservedMachineRange(184, ReservedMachinesLookup.ARM_OWNER),
                new InclusiveReservedMachineRange(205, 209, ReservedMachinesLookup.INTEL_OWNER),
                new InclusiveReservedMachineRange(225, 242),
                new InclusiveReservedMachineRange(261, ReservedMachinesLookup.TACHYUM_PROCESSOR_OWNER)
        );
    }

    /**
     * Checks if the given machine number is marked as a reserved machine.
     *
     * @param machineNumber The raw number in the {@code e_machine} field of the header.
     * @return True if the given machine number is reserved for future use.
     * @apiNote If the function returns false it can be either because the machine number is a defined
     * machine, OR it is a non-registered/invalid machine.
     */
    public boolean isReservedMachine(short machineNumber) {
        for (InclusiveReservedMachineRange reservedRange : sortedReservedMachinesRanges) {
            // List is sorted, if the number is less than the range start it definitely isn't reserved
            if (Short.compareUnsigned(machineNumber, reservedRange.rangeStart) < 0) {
                return false;
            }
            if (reservedRange.isInRange(machineNumber)) {
                return true;
            }
        }
        return false;
    }

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
    public Optional<String> getReservedMachineOwner(short machineNumber) {
        for (InclusiveReservedMachineRange reservedRange : sortedReservedMachinesRanges) {
            // List is sorted, if the number is less than the range start it definitely isn't reserved
            if (Short.compareUnsigned(machineNumber, reservedRange.rangeStart) < 0) {
                return Optional.empty();
            }
            if (reservedRange.isInRange(machineNumber)) {
                return reservedRange.owner;
            }
        }
        return Optional.empty();
    }

    @VisibleForTesting
    static class InclusiveReservedMachineRange {
        public short rangeStart;
        public short rangeEnd;
        @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
        public Optional<String> owner;

        public InclusiveReservedMachineRange(int onlyReserved) {
            rangeStart = rangeEnd = (short) onlyReserved;
            this.owner = Optional.empty();
        }

        public InclusiveReservedMachineRange(int onlyReserved, String owner) {
            this(onlyReserved);
            this.owner = Optional.of(owner);
        }

        public InclusiveReservedMachineRange(int rangeStart, int rangeEnd) {
            this.rangeStart = (short) rangeStart;
            this.rangeEnd = (short) rangeEnd;
            this.owner = Optional.empty();
        }

        public InclusiveReservedMachineRange(int rangeStart, int rangeEnd, String owner) {
            this(rangeStart, rangeEnd);
            this.owner = Optional.of(owner);
        }

        public boolean isInRange(short value) {
            boolean geFirst = Short.compareUnsigned(rangeStart, value) <= 0;
            boolean leLast = Short.compareUnsigned(value, rangeEnd) <= 0;
            return geFirst && leLast;
        }
    }
}
