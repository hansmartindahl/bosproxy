package org.dahlnet.bosproxy.dto;

import java.util.List;

public record PoolGroupConfigurationDTO(
        String uid,
        String name,
        Integer quota,
        Double fixedShareRatio,
        List<PoolConfigurationDTO> pools
) {}
