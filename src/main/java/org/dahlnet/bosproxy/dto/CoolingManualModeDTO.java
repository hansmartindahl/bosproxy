package org.dahlnet.bosproxy.dto;

public record CoolingManualModeDTO(
        Double fanSpeedRatio,
        double hotTemperature,
        double dangerousTemperature,
        double targetTemperature,
        Integer minimumRequiredFans
) {}
