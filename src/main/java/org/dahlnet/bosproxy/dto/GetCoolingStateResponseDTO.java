package org.dahlnet.bosproxy.dto;

import java.util.List;

public record GetCoolingStateResponseDTO(List<FanStateDTO> fans, TemperatureSensorDTO highestTemperature) {}
