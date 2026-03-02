package org.dahlnet.bosproxy.dto;

public record MinerPowerStatsDTO(
        long approximatedConsumptionWatt,
        double efficiencyJoulePerTerahash
) {}
