package org.dahlnet.bosproxy.dto;

import java.util.List;

public record StaticNetworkDTO(String address, String netmask, String gateway, List<String> dnsServers) {}
