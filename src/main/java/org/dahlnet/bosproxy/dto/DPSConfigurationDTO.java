package org.dahlnet.bosproxy.dto;

public record DPSConfigurationDTO(
        Boolean enabled,
        Long powerStepWatt,
        Double hashrateStepThs,
        Long minPowerTargetWatt,
        Double minHashrateTargetThs,
        Boolean shutdownEnabled,
        Integer shutdownDurationHours,
        String mode
) {}
