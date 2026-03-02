package org.dahlnet.bosproxy.dto;

public record GetMinerStatsResponseDTO(
        PoolStatsDTO poolStats,
        WorkSolverStatsDTO minerStats,
        MinerPowerStatsDTO powerStats
) {}
