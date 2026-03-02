package org.dahlnet.bosproxy.dto;

import java.util.List;

public record GetNetworkInfoResponseDTO(
        String name,
        String macAddress,
        String hostname,
        String protocol,
        List<String> dnsServers,
        List<IpNetworkDTO> networks,
        String defaultGateway
) {}
