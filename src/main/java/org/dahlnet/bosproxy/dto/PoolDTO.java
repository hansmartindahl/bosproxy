package org.dahlnet.bosproxy.dto;

public record PoolDTO(
        String uid,
        String url,
        String user,
        boolean enabled,
        boolean alive,
        boolean active,
        PoolStatsDTO stats
) {}
