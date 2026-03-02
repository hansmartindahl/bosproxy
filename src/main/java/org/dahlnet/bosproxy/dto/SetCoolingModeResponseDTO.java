package org.dahlnet.bosproxy.dto;

public record SetCoolingModeResponseDTO(
        CoolingAutoModeDTO auto,
        CoolingManualModeDTO manual,
        CoolingImmersionModeDTO immersion,
        CoolingHydroModeDTO hydro
) {}
