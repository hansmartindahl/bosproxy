package org.dahlnet.bosproxy.dto;

public record GetMinerDetailsResponseDTO(
        String uid,
        MinerIdentityDTO minerIdentity,
        String platform,
        String bosMode,
        BosVersionDTO bosVersion,
        String hostname,
        String macAddress,
        long bosminerUptimeS,
        long systemUptimeS,
        String status,
        String kernelVersion,
        String serialNumber
) {}
