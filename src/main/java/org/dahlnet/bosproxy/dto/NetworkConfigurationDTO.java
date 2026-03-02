package org.dahlnet.bosproxy.dto;

public record NetworkConfigurationDTO(Boolean useDhcp, StaticNetworkDTO staticConfig, String hostname) {}
