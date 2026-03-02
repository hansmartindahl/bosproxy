package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.Configuration;
import braiins.bos.v1.Cooling;
import braiins.bos.v1.Performance;

import java.util.List;
import java.util.stream.Collectors;

public final class ConfigurationMapper {

    private ConfigurationMapper() {}

    public static GetMinerConfigurationResponseDTO toGetMinerConfigurationResponseDTO(Configuration.GetMinerConfigurationResponse r) {
        List<PoolGroupConfigurationDTO> poolGroups = r.getPoolGroupsList().stream()
                .map(PoolMapper::toPoolGroupConfigurationDTO)
                .collect(Collectors.toList());
        CoolingConfigurationDTO temperature = r.hasTemperature() ? toCoolingConfigurationDTO(r.getTemperature()) : null;
        TunerConfigurationDTO tuner = r.hasTuner() ? toTunerConfigurationDTO(r.getTuner()) : null;
        DPSConfigurationDTO dps = r.hasDps() ? toDPSConfigurationDTO(r.getDps()) : null;
        HashboardPerformanceConfigurationDTO hashboardConfig = r.hasHashboardConfig() ? toHashboardPerformanceConfigurationDTO(r.getHashboardConfig()) : null;
        return new GetMinerConfigurationResponseDTO(poolGroups, temperature, tuner, dps, hashboardConfig);
    }

    static CoolingConfigurationDTO toCoolingConfigurationDTO(Cooling.CoolingConfiguration c) {
        CoolingAutoModeDTO auto = null;
        CoolingManualModeDTO manual = null;
        CoolingImmersionModeDTO immersion = null;
        CoolingHydroModeDTO hydro = null;
        switch (c.getModeCase()) {
            case AUTO -> auto = CoolingMapper.toCoolingAutoModeDTO(c.getAuto());
            case MANUAL -> manual = CoolingMapper.toCoolingManualModeDTO(c.getManual());
            case IMMERSION -> immersion = CoolingMapper.toCoolingImmersionModeDTO(c.getImmersion());
            case HYDRO -> hydro = CoolingMapper.toCoolingHydroModeDTO(c.getHydro());
            default -> {}
        }
        return new CoolingConfigurationDTO(auto, manual, immersion, hydro);
    }

    static TunerConfigurationDTO toTunerConfigurationDTO(Performance.TunerConfiguration t) {
        return new TunerConfigurationDTO(
                t.hasEnabled() ? t.getEnabled() : null,
                t.hasTunerMode() ? t.getTunerMode().name() : null,
                t.hasPowerTarget() ? t.getPowerTarget().getWatt() : null,
                t.hasHashrateTarget() ? t.getHashrateTarget().getTerahashPerSecond() : null
        );
    }

    static DPSConfigurationDTO toDPSConfigurationDTO(Performance.DPSConfiguration d) {
        return new DPSConfigurationDTO(
                d.hasEnabled() ? d.getEnabled() : null,
                d.hasPowerStep() ? d.getPowerStep().getWatt() : null,
                d.hasHashrateStep() ? d.getHashrateStep().getTerahashPerSecond() : null,
                d.hasMinPowerTarget() ? d.getMinPowerTarget().getWatt() : null,
                d.hasMinHashrateTarget() ? d.getMinHashrateTarget().getTerahashPerSecond() : null,
                d.hasShutdownEnabled() ? d.getShutdownEnabled() : null,
                d.hasShutdownDuration() ? d.getShutdownDuration().getHours() : null,
                d.hasMode() ? d.getMode().name() : null
        );
    }

    static HashboardPerformanceConfigurationDTO toHashboardPerformanceConfigurationDTO(Performance.HashboardPerformanceConfiguration h) {
        double globalFreq = h.hasGlobalFrequency() ? h.getGlobalFrequency().getHertz() : 0;
        double globalVolt = h.hasGlobalVoltage() ? h.getGlobalVoltage().getVolt() : 0;
        List<HashboardConfigDTO> boards = h.getHashboardsList().stream()
                .map(ConfigurationMapper::toHashboardConfigDTO)
                .collect(Collectors.toList());
        return new HashboardPerformanceConfigurationDTO(globalFreq, globalVolt, boards);
    }

    static HashboardConfigDTO toHashboardConfigDTO(Performance.HashboardConfig b) {
        return new HashboardConfigDTO(
                b.getId(),
                b.hasEnabled() ? b.getEnabled() : null,
                b.hasFrequency() ? b.getFrequency().getHertz() : null,
                b.hasVoltage() ? b.getVoltage().getVolt() : null
        );
    }

    public static GetConstraintsResponseDTO toGetConstraintsResponseDTO(Configuration.GetConstraintsResponse r) {
        String tunerConstraints = r.hasTunerConstraints() ? r.getTunerConstraints().getDefaultMode().name() : null;
        String coolingConstraints = r.hasCoolingConstraints() ? r.getCoolingConstraints().getDefaultCoolingMode().name() : null;
        String dpsConstraints = r.hasDpsConstraints() ? r.getDpsConstraints().getMode().name() : null;
        List<String> hashboardIds = r.hasHashboardsConstraints() ? r.getHashboardsConstraints().getHashboardIdsList().stream().collect(Collectors.toList()) : List.of();
        return new GetConstraintsResponseDTO(tunerConstraints, coolingConstraints, dpsConstraints, hashboardIds);
    }
}
