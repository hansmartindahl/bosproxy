package org.dahlnet.bosproxy.dto;

public record TunerConfigurationDTO(Boolean enabled, String tunerMode, Long powerTargetWatt, Double hashrateTargetThs) {}
