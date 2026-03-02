package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.Common;
import braiins.bos.v1.Performance;

import java.util.List;
import java.util.stream.Collectors;

public final class PerformanceMapper {

    private PerformanceMapper() {}

    public static GetTunerStateResponseDTO toGetTunerStateResponseDTO(Performance.GetTunerStateResponse r) {
        String overall = r.getOverallTunerState().name();
        String modeState = null;
        switch (r.getModeStateCase()) {
            case POWER_TARGET_MODE_STATE -> modeState = "POWER_TARGET";
            case HASHRATE_TARGET_MODE_STATE -> modeState = "HASHRATE_TARGET";
            default -> {}
        }
        return new GetTunerStateResponseDTO(overall, modeState);
    }

    public static ListTargetProfilesResponseDTO toListTargetProfilesResponseDTO(Performance.ListTargetProfilesResponse r) {
        List<Object> power = r.getPowerTargetProfilesList().stream().map(p -> (Object) Long.valueOf(p.getTarget().getWatt())).collect(Collectors.toList());
        List<Object> hashrate = r.getHashrateTargetProfilesList().stream().map(h -> (Object) Double.valueOf(h.getTarget().getTerahashPerSecond())).collect(Collectors.toList());
        return new ListTargetProfilesResponseDTO(power, hashrate);
    }

    public static SetPowerTargetResponseDTO toSetPowerTargetResponseDTO(Performance.SetPowerTargetResponse r) {
        long watt = r.hasPowerTarget() ? r.getPowerTarget().getWatt() : 0;
        return new SetPowerTargetResponseDTO(watt);
    }

    public static SetHashrateTargetResponseDTO toSetHashrateTargetResponseDTO(Performance.SetHashrateTargetResponse r) {
        double ths = r.hasHashrateTarget() ? r.getHashrateTarget().getTerahashPerSecond() : 0;
        return new SetHashrateTargetResponseDTO(ths);
    }

    public static SetDPSResponseDTO toSetDPSResponseDTO(Performance.SetDPSResponse r) {
        return new SetDPSResponseDTO(
                r.hasEnabled() ? r.getEnabled() : null,
                r.hasShutdownEnabled() ? r.getShutdownEnabled() : null,
                r.hasShutdownDuration() ? r.getShutdownDuration().getHours() : null,
                r.hasMode() ? r.getMode().name() : null
        );
    }

    public static PerformanceModeDTO toPerformanceModeDTO(Performance.PerformanceMode m) {
        String mode = switch (m.getModeCase()) {
            case MANUAL_MODE -> "MANUAL";
            case TUNER_MODE -> "TUNER";
            default -> "MODE_NOT_SET";
        };
        return new PerformanceModeDTO(mode);
    }

    public static Performance.SetDefaultPowerTargetRequest toSetDefaultPowerTargetRequest(SetPowerTargetRequestDTO dto) {
        return Performance.SetDefaultPowerTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }

    public static Performance.SetPowerTargetRequest toSetPowerTargetRequest(SetPowerTargetRequestDTO dto) {
        Performance.SetPowerTargetRequest.Builder b = Performance.SetPowerTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.powerTargetWatt() != null) b.setPowerTarget(braiins.bos.v1.Units.Power.newBuilder().setWatt(dto.powerTargetWatt()).build());
        return b.build();
    }

    public static Performance.IncrementPowerTargetRequest toIncrementPowerTargetRequest(SetPowerTargetRequestDTO dto) {
        return Performance.IncrementPowerTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }

    public static Performance.DecrementPowerTargetRequest toDecrementPowerTargetRequest(SetPowerTargetRequestDTO dto) {
        return Performance.DecrementPowerTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }

    public static Performance.SetDefaultHashrateTargetRequest toSetDefaultHashrateTargetRequest(SetHashrateTargetRequestDTO dto) {
        return Performance.SetDefaultHashrateTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }

    public static Performance.SetHashrateTargetRequest toSetHashrateTargetRequest(SetHashrateTargetRequestDTO dto) {
        Performance.SetHashrateTargetRequest.Builder b = Performance.SetHashrateTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.hashrateTargetThs() != null) b.setHashrateTarget(braiins.bos.v1.Units.TeraHashrate.newBuilder().setTerahashPerSecond(dto.hashrateTargetThs()).build());
        return b.build();
    }

    public static Performance.IncrementHashrateTargetRequest toIncrementHashrateTargetRequest(SetHashrateTargetRequestDTO dto) {
        return Performance.IncrementHashrateTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }

    public static Performance.DecrementHashrateTargetRequest toDecrementHashrateTargetRequest(SetHashrateTargetRequestDTO dto) {
        return Performance.DecrementHashrateTargetRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }

    public static Performance.SetDPSRequest toSetDPSRequest(SetDPSRequestDTO dto) {
        Performance.SetDPSRequest.Builder b = Performance.SetDPSRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.enable() != null) b.setEnable(dto.enable());
        if (dto.enableShutdown() != null) b.setEnableShutdown(dto.enableShutdown());
        if (dto.shutdownDurationHours() != null) b.setShutdownDuration(braiins.bos.v1.Units.Hours.newBuilder().setHours(dto.shutdownDurationHours()).build());
        if (dto.mode() != null) b.setMode(Performance.DPSMode.valueOf(dto.mode()));
        return b.build();
    }

    public static Performance.SetPerformanceModeRequest toSetPerformanceModeRequest(SetPerformanceModeRequestDTO dto) {
        return Performance.SetPerformanceModeRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .build();
    }
}
