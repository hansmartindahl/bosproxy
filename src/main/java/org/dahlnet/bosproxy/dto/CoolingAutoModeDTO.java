package org.dahlnet.bosproxy.dto;

public record CoolingAutoModeDTO(
        double targetTemperature,
        double hotTemperature,
        double dangerousTemperature,
        Integer minFanSpeed,
        Integer maxFanSpeed,
        Integer minimumRequiredFans
) {}
