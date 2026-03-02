package org.dahlnet.bosproxy.dto;

import java.util.List;

public record GetMinerConfigurationResponseDTO(
        List<PoolGroupConfigurationDTO> poolGroups,
        CoolingConfigurationDTO temperature,
        TunerConfigurationDTO tuner,
        DPSConfigurationDTO dps,
        HashboardPerformanceConfigurationDTO hashboardConfig
) {}
