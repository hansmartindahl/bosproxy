package org.dahlnet.bosproxy.dto;

import java.time.Instant;

public record PoolStatsDTO(
        long acceptedShares,
        long rejectedShares,
        long staleShares,
        long lastDifficulty,
        long bestShare,
        long generatedWork,
        Instant lastShareTime
) {}
