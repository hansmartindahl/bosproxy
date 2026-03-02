package org.dahlnet.bosproxy.dto;

public record WorkSolverStatsDTO(
        RealHashrateDTO realHashrate,
        double nominalHashrate,
        double errorHashrate,
        int foundBlocks,
        long bestShare
) {}
