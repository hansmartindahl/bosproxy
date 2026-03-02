package org.dahlnet.bosproxy.dto;

public record RealHashrateDTO(
        double last5s,
        double last15s,
        double last30s,
        double last1m,
        double last5m,
        double last15m,
        double last30m,
        double last1h,
        double last24h,
        double sinceRestart
) {}
