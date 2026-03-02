package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.Cooling;
import braiins.bos.v1.Common;

import java.util.List;
import java.util.stream.Collectors;

public final class CoolingMapper {

    private CoolingMapper() {}

    public static GetCoolingStateResponseDTO toGetCoolingStateResponseDTO(Cooling.GetCoolingStateResponse r) {
        List<FanStateDTO> fans = r.getFansList().stream()
                .map(CoolingMapper::toFanStateDTO)
                .collect(Collectors.toList());
        TemperatureSensorDTO highest = r.hasHighestTemperature() ? toTemperatureSensorDTO(r.getHighestTemperature()) : null;
        return new GetCoolingStateResponseDTO(fans, highest);
    }

    static FanStateDTO toFanStateDTO(Cooling.FanState f) {
        return new FanStateDTO(
                f.hasPosition() ? f.getPosition() : null,
                f.getRpm(),
                f.hasTargetSpeedRatio() ? f.getTargetSpeedRatio() : null
        );
    }

    static TemperatureSensorDTO toTemperatureSensorDTO(Cooling.TemperatureSensor t) {
        double temp = t.hasTemperature() ? t.getTemperature().getDegreeC() : 0;
        return new TemperatureSensorDTO(
                t.hasId() ? t.getId() : null,
                t.getLocation().name(),
                temp
        );
    }

    public static SetCoolingModeResponseDTO toSetCoolingModeResponseDTO(Cooling.SetCoolingModeResponse r) {
        CoolingAutoModeDTO auto = null;
        CoolingManualModeDTO manual = null;
        CoolingImmersionModeDTO immersion = null;
        CoolingHydroModeDTO hydro = null;
        switch (r.getModeCase()) {
            case AUTO -> auto = toCoolingAutoModeDTO(r.getAuto());
            case MANUAL -> manual = toCoolingManualModeDTO(r.getManual());
            case IMMERSION -> immersion = toCoolingImmersionModeDTO(r.getImmersion());
            case HYDRO -> hydro = toCoolingHydroModeDTO(r.getHydro());
            default -> {}
        }
        return new SetCoolingModeResponseDTO(auto, manual, immersion, hydro);
    }

    static CoolingAutoModeDTO toCoolingAutoModeDTO(Cooling.CoolingAutoMode m) {
        return new CoolingAutoModeDTO(
                m.hasTargetTemperature() ? m.getTargetTemperature().getDegreeC() : 0,
                m.hasHotTemperature() ? m.getHotTemperature().getDegreeC() : 0,
                m.hasDangerousTemperature() ? m.getDangerousTemperature().getDegreeC() : 0,
                m.hasMinFanSpeed() ? m.getMinFanSpeed() : null,
                m.hasMaxFanSpeed() ? m.getMaxFanSpeed() : null,
                m.hasMinimumRequiredFans() ? m.getMinimumRequiredFans() : null
        );
    }

    static CoolingManualModeDTO toCoolingManualModeDTO(Cooling.CoolingManualMode m) {
        return new CoolingManualModeDTO(
                m.hasFanSpeedRatio() ? m.getFanSpeedRatio() : null,
                m.hasHotTemperature() ? m.getHotTemperature().getDegreeC() : 0,
                m.hasDangerousTemperature() ? m.getDangerousTemperature().getDegreeC() : 0,
                m.hasTargetTemperature() ? m.getTargetTemperature().getDegreeC() : 0,
                m.hasMinimumRequiredFans() ? m.getMinimumRequiredFans() : null
        );
    }

    static CoolingImmersionModeDTO toCoolingImmersionModeDTO(Cooling.CoolingImmersionMode m) {
        return new CoolingImmersionModeDTO(
                m.hasHotTemperature() ? m.getHotTemperature().getDegreeC() : 0,
                m.hasDangerousTemperature() ? m.getDangerousTemperature().getDegreeC() : 0,
                m.hasTargetTemperature() ? m.getTargetTemperature().getDegreeC() : 0
        );
    }

    static CoolingHydroModeDTO toCoolingHydroModeDTO(Cooling.CoolingHydroMode m) {
        return new CoolingHydroModeDTO(
                m.hasHotTemperature() ? m.getHotTemperature().getDegreeC() : 0,
                m.hasDangerousTemperature() ? m.getDangerousTemperature().getDegreeC() : 0,
                m.hasTargetTemperature() ? m.getTargetTemperature().getDegreeC() : 0
        );
    }

    public static Cooling.SetCoolingModeRequest toSetCoolingModeRequest(SetCoolingModeRequestDTO dto) {
        Cooling.SetCoolingModeRequest.Builder b = Cooling.SetCoolingModeRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.auto() != null) b.setAuto(toCoolingAutoModeProto(dto.auto()));
        else if (dto.manual() != null) b.setManual(toCoolingManualModeProto(dto.manual()));
        else if (dto.immersion() != null) b.setImmersion(toCoolingImmersionModeProto(dto.immersion()));
        else if (dto.hydro() != null) b.setHydro(toCoolingHydroModeProto(dto.hydro()));
        return b.build();
    }

    static Cooling.CoolingAutoMode toCoolingAutoModeProto(CoolingAutoModeDTO dto) {
        Cooling.CoolingAutoMode.Builder b = Cooling.CoolingAutoMode.newBuilder()
                .setTargetTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.targetTemperature()).build())
                .setHotTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.hotTemperature()).build())
                .setDangerousTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.dangerousTemperature()).build());
        if (dto.minFanSpeed() != null) b.setMinFanSpeed(dto.minFanSpeed());
        if (dto.maxFanSpeed() != null) b.setMaxFanSpeed(dto.maxFanSpeed());
        if (dto.minimumRequiredFans() != null) b.setMinimumRequiredFans(dto.minimumRequiredFans());
        return b.build();
    }

    static Cooling.CoolingManualMode toCoolingManualModeProto(CoolingManualModeDTO dto) {
        Cooling.CoolingManualMode.Builder b = Cooling.CoolingManualMode.newBuilder()
                .setHotTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.hotTemperature()).build())
                .setDangerousTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.dangerousTemperature()).build())
                .setTargetTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.targetTemperature()).build());
        if (dto.fanSpeedRatio() != null) b.setFanSpeedRatio(dto.fanSpeedRatio());
        if (dto.minimumRequiredFans() != null) b.setMinimumRequiredFans(dto.minimumRequiredFans());
        return b.build();
    }

    static Cooling.CoolingImmersionMode toCoolingImmersionModeProto(CoolingImmersionModeDTO dto) {
        return Cooling.CoolingImmersionMode.newBuilder()
                .setHotTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.hotTemperature()).build())
                .setDangerousTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.dangerousTemperature()).build())
                .setTargetTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.targetTemperature()).build())
                .build();
    }

    static Cooling.CoolingHydroMode toCoolingHydroModeProto(CoolingHydroModeDTO dto) {
        return Cooling.CoolingHydroMode.newBuilder()
                .setHotTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.hotTemperature()).build())
                .setDangerousTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.dangerousTemperature()).build())
                .setTargetTemperature(braiins.bos.v1.Units.Temperature.newBuilder().setDegreeC(dto.targetTemperature()).build())
                .build();
    }
}
