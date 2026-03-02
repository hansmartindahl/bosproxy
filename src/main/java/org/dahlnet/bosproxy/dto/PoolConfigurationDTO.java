package org.dahlnet.bosproxy.dto;

public record PoolConfigurationDTO(
        String uid,
        String url,
        String user,
        String password,
        Boolean enabled
) {}
