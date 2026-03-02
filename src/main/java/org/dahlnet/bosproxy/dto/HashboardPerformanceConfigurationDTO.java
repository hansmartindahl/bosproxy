package org.dahlnet.bosproxy.dto;

import java.util.List;

public record HashboardPerformanceConfigurationDTO(Double globalFrequencyHz, Double globalVoltage, List<HashboardConfigDTO> hashboards) {}
