package org.dahlnet.bosproxy.dto;

import java.util.List;

public record PoolGroupDTO(
        String name,
        Integer quota,
        Double fixedShareRatio,
        List<PoolDTO> pools
) {}
