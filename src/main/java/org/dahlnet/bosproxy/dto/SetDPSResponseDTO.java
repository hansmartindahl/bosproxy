package org.dahlnet.bosproxy.dto;

public record SetDPSResponseDTO(Boolean enabled, Boolean shutdownEnabled, Integer shutdownDurationHours, String mode) {}
