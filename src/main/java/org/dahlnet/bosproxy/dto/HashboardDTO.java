package org.dahlnet.bosproxy.dto;

public record HashboardDTO(
        String id,
        boolean enabled,
        Integer chipsCount,
        Double currentVoltage,
        Double currentFrequency,
        Double highestChipTemp,
        Double boardTemp,
        WorkSolverStatsDTO stats,
        String model,
        Double lowestInletTemp,
        Double highestOutletTemp,
        String serialNumber,
        String boardName,
        String chipType
) {}
