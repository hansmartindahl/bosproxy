package org.dahlnet.bosproxy.dto;

public record SetDPSRequestDTO(
        String saveAction,
        Boolean enable,
        Boolean enableShutdown,
        Integer shutdownDurationHours,
        String mode
) {}
