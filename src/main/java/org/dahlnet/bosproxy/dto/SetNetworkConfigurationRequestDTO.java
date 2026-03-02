package org.dahlnet.bosproxy.dto;

public record SetNetworkConfigurationRequestDTO(Boolean useDhcp, StaticNetworkDTO staticConfig, String hostname) {}
