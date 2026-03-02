package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.Miner;
import braiins.bos.v1.PoolOuterClass;
import braiins.bos.v1.Work;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public final class MinerMapper {

    private MinerMapper() {}

    public static GetMinerStatsResponseDTO toGetMinerStatsResponseDTO(braiins.bos.v1.Miner.GetMinerStatsResponse r) {
        PoolStatsDTO poolStats = r.hasPoolStats() ? toPoolStatsDTO(r.getPoolStats()) : null;
        WorkSolverStatsDTO minerStats = r.hasMinerStats() ? toWorkSolverStatsDTO(r.getMinerStats()) : null;
        MinerPowerStatsDTO powerStats = r.hasPowerStats() ? toMinerPowerStatsDTO(r.getPowerStats()) : null;
        return new GetMinerStatsResponseDTO(poolStats, minerStats, powerStats);
    }

    static PoolStatsDTO toPoolStatsDTO(PoolOuterClass.PoolStats s) {
        Instant lastShare = s.hasLastShareTime()
                ? Instant.ofEpochSecond(s.getLastShareTime().getSeconds(), s.getLastShareTime().getNanos())
                : null;
        return new PoolStatsDTO(
                s.getAcceptedShares(),
                s.getRejectedShares(),
                s.getStaleShares(),
                s.getLastDifficulty(),
                s.getBestShare(),
                s.getGeneratedWork(),
                lastShare
        );
    }

    static RealHashrateDTO toRealHashrateDTO(Work.RealHashrate r) {
        if (r == null) return null;
        return new RealHashrateDTO(
                r.hasLast5S() ? r.getLast5S().getGigahashPerSecond() : 0,
                r.hasLast15S() ? r.getLast15S().getGigahashPerSecond() : 0,
                r.hasLast30S() ? r.getLast30S().getGigahashPerSecond() : 0,
                r.hasLast1M() ? r.getLast1M().getGigahashPerSecond() : 0,
                r.hasLast5M() ? r.getLast5M().getGigahashPerSecond() : 0,
                r.hasLast15M() ? r.getLast15M().getGigahashPerSecond() : 0,
                r.hasLast30M() ? r.getLast30M().getGigahashPerSecond() : 0,
                r.hasLast1H() ? r.getLast1H().getGigahashPerSecond() : 0,
                r.hasLast24H() ? r.getLast24H().getGigahashPerSecond() : 0,
                r.hasSinceRestart() ? r.getSinceRestart().getGigahashPerSecond() : 0
        );
    }

    static WorkSolverStatsDTO toWorkSolverStatsDTO(Work.WorkSolverStats s) {
        RealHashrateDTO real = s.hasRealHashrate() ? toRealHashrateDTO(s.getRealHashrate()) : null;
        double nominal = s.hasNominalHashrate() ? s.getNominalHashrate().getGigahashPerSecond() : 0;
        double error = s.hasErrorHashrate() ? s.getErrorHashrate().getMegahashPerSecond() : 0;
        return new WorkSolverStatsDTO(real, nominal, error, s.getFoundBlocks(), s.getBestShare());
    }

    static MinerPowerStatsDTO toMinerPowerStatsDTO(Miner.MinerPowerStats s) {
        long watt = s.hasApproximatedConsumption() ? s.getApproximatedConsumption().getWatt() : 0;
        double eff = s.hasEfficiency() ? s.getEfficiency().getJoulePerTerahash() : 0;
        return new MinerPowerStatsDTO(watt, eff);
    }

    public static GetMinerDetailsResponseDTO toGetMinerDetailsResponseDTO(Miner.GetMinerDetailsResponse r) {
        MinerIdentityDTO identity = r.hasMinerIdentity()
                ? new MinerIdentityDTO(r.getMinerIdentity().getBrand().name(), r.getMinerIdentity().getName(), r.getMinerIdentity().getMinerModel())
                : null;
        BosVersionDTO version = r.hasBosVersion()
                ? new BosVersionDTO(r.getBosVersion().getCurrent(), r.getBosVersion().getMajor(), r.getBosVersion().getBosPlus())
                : null;
        return new GetMinerDetailsResponseDTO(
                r.getUid(),
                identity,
                r.getPlatform().name(),
                r.getBosMode().name(),
                version,
                r.getHostname(),
                r.getMacAddress(),
                r.getBosminerUptimeS(),
                r.getSystemUptimeS(),
                r.getStatus().name(),
                r.getKernelVersion(),
                r.hasSerialNumber() ? r.getSerialNumber() : null
        );
    }

    public static GetErrorsResponseDTO toGetErrorsResponseDTO(Miner.GetErrorsResponse r) {
        List<MinerErrorDTO> errors = r.getErrorsList().stream()
                .map(MinerMapper::toMinerErrorDTO)
                .collect(Collectors.toList());
        return new GetErrorsResponseDTO(errors);
    }

    static MinerErrorDTO toMinerErrorDTO(Miner.MinerError e) {
        List<ErrorCodeDTO> codes = e.getErrorCodesList().stream()
                .map(c -> new ErrorCodeDTO(c.getCode(), c.getReason(), c.getHint()))
                .collect(Collectors.toList());
        List<ComponentDTO> comps = e.getComponentsList().stream()
                .map(c -> new ComponentDTO(c.getName(), c.getIndex()))
                .collect(Collectors.toList());
        return new MinerErrorDTO(e.getTimestamp(), e.getMessage(), codes, comps);
    }

    public static GetHashboardsResponseDTO toGetHashboardsResponseDTO(Miner.GetHashboardsResponse r) {
        List<HashboardDTO> boards = r.getHashboardsList().stream()
                .map(MinerMapper::toHashboardDTO)
                .collect(Collectors.toList());
        return new GetHashboardsResponseDTO(boards);
    }

    static HashboardDTO toHashboardDTO(Miner.Hashboard h) {
        WorkSolverStatsDTO stats = h.hasStats() ? toWorkSolverStatsDTO(h.getStats()) : null;
        return new HashboardDTO(
                h.getId(),
                h.getEnabled(),
                h.hasChipsCount() ? h.getChipsCount().getValue() : null,
                h.hasCurrentVoltage() ? h.getCurrentVoltage().getVolt() : null,
                h.hasCurrentFrequency() ? h.getCurrentFrequency().getHertz() : null,
                h.hasHighestChipTemp() && h.getHighestChipTemp().hasTemperature() ? h.getHighestChipTemp().getTemperature().getDegreeC() : null,
                h.hasBoardTemp() ? h.getBoardTemp().getDegreeC() : null,
                stats,
                h.hasModel() ? h.getModel() : null,
                h.hasLowestInletTemp() ? h.getLowestInletTemp().getDegreeC() : null,
                h.hasHighestOutletTemp() ? h.getHighestOutletTemp().getDegreeC() : null,
                h.hasSerialNumber() ? h.getSerialNumber() : null,
                h.hasBoardName() ? h.getBoardName() : null,
                h.hasChipType() ? h.getChipType() : null
        );
    }

    public static List<HashboardEnableStateDTO> toHashboardEnableStateDTOs(List<Miner.HashboardEnableState> list) {
        return list.stream()
                .map(s -> new HashboardEnableStateDTO(s.getId(), s.getIsEnabled()))
                .collect(Collectors.toList());
    }
}
