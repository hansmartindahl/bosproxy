package org.dahlnet.bosproxy.dto;

public record SetCoolingModeRequestDTO(
        String saveAction,
        CoolingAutoModeDTO auto,
        CoolingManualModeDTO manual,
        CoolingImmersionModeDTO immersion,
        CoolingHydroModeDTO hydro
) {}
