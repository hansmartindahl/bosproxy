package org.dahlnet.bosproxy.dto;

public record CoolingConfigurationDTO(
        CoolingAutoModeDTO auto,
        CoolingManualModeDTO manual,
        CoolingImmersionModeDTO immersion,
        CoolingHydroModeDTO hydro
) {}
