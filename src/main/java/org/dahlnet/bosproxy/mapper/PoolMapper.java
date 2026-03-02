package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.Common;
import braiins.bos.v1.PoolOuterClass;

import java.util.List;
import java.util.stream.Collectors;

public final class PoolMapper {

    private PoolMapper() {}

    public static GetPoolGroupsResponseDTO toGetPoolGroupsResponseDTO(PoolOuterClass.GetPoolGroupsResponse r) {
        List<PoolGroupDTO> groups = r.getPoolGroupsList().stream()
                .map(PoolMapper::toPoolGroupDTO)
                .collect(Collectors.toList());
        return new GetPoolGroupsResponseDTO(groups);
    }

    static PoolGroupDTO toPoolGroupDTO(PoolOuterClass.PoolGroup g) {
        Integer quota = null;
        Double fixedShareRatio = null;
        switch (g.getStrategyCase()) {
            case QUOTA -> quota = g.getQuota().getValue();
            case FIXED_SHARE_RATIO -> fixedShareRatio = g.getFixedShareRatio().getValue();
            default -> {}
        }
        List<PoolDTO> pools = g.getPoolsList().stream().map(PoolMapper::toPoolDTO).collect(Collectors.toList());
        return new PoolGroupDTO(g.getName(), quota, fixedShareRatio, pools);
    }

    static PoolDTO toPoolDTO(PoolOuterClass.Pool p) {
        PoolStatsDTO stats = p.hasStats() ? MinerMapper.toPoolStatsDTO(p.getStats()) : null;
        return new PoolDTO(
                p.getUid(),
                p.getUrl(),
                p.getUser(),
                p.getEnabled(),
                p.getAlive(),
                p.getActive(),
                stats
        );
    }

    public static PoolGroupConfigurationDTO toPoolGroupConfigurationDTO(PoolOuterClass.PoolGroupConfiguration g) {
        Integer quota = null;
        Double fixedShareRatio = null;
        switch (g.getLoadBalanceStrategyCase()) {
            case QUOTA -> quota = g.getQuota().getValue();
            case FIXED_SHARE_RATIO -> fixedShareRatio = g.getFixedShareRatio().getValue();
            default -> {}
        }
        List<PoolConfigurationDTO> pools = g.getPoolsList().stream()
                .map(PoolMapper::toPoolConfigurationDTO)
                .collect(Collectors.toList());
        return new PoolGroupConfigurationDTO(
                g.getUid(),
                g.getName(),
                quota,
                fixedShareRatio,
                pools
        );
    }

    static PoolConfigurationDTO toPoolConfigurationDTO(PoolOuterClass.PoolConfiguration p) {
        return new PoolConfigurationDTO(
                p.getUid(),
                p.getUrl(),
                p.getUser(),
                p.hasPassword() ? p.getPassword() : null,
                p.hasEnabled() ? p.getEnabled() : null
        );
    }

    public static CreatePoolGroupResponseDTO toCreatePoolGroupResponseDTO(PoolOuterClass.CreatePoolGroupResponse r) {
        return new CreatePoolGroupResponseDTO(r.hasGroup() ? toPoolGroupConfigurationDTO(r.getGroup()) : null);
    }

    public static UpdatePoolGroupResponseDTO toUpdatePoolGroupResponseDTO(PoolOuterClass.UpdatePoolGroupResponse r) {
        return new UpdatePoolGroupResponseDTO(r.hasGroup() ? toPoolGroupConfigurationDTO(r.getGroup()) : null);
    }

    public static SetPoolGroupsResponseDTO toSetPoolGroupsResponseDTO(PoolOuterClass.SetPoolGroupsResponse r) {
        List<PoolGroupConfigurationDTO> groups = r.getPoolGroupsList().stream()
                .map(PoolMapper::toPoolGroupConfigurationDTO)
                .collect(Collectors.toList());
        return new SetPoolGroupsResponseDTO(groups);
    }

    public static PoolOuterClass.CreatePoolGroupRequest toCreatePoolGroupRequest(CreatePoolGroupRequestDTO dto) {
        PoolOuterClass.CreatePoolGroupRequest.Builder b = PoolOuterClass.CreatePoolGroupRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.group() != null) {
            b.setGroup(toPoolGroupConfigurationProto(dto.group()));
        }
        return b.build();
    }

    public static PoolOuterClass.UpdatePoolGroupRequest toUpdatePoolGroupRequest(UpdatePoolGroupRequestDTO dto) {
        PoolOuterClass.UpdatePoolGroupRequest.Builder b = PoolOuterClass.UpdatePoolGroupRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.group() != null) {
            b.setGroup(toPoolGroupConfigurationProto(dto.group()));
        }
        return b.build();
    }

    public static PoolOuterClass.RemovePoolGroupRequest toRemovePoolGroupRequest(RemovePoolGroupRequestDTO dto) {
        return PoolOuterClass.RemovePoolGroupRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()))
                .setUid(dto.uid())
                .build();
    }

    public static PoolOuterClass.SetPoolGroupsRequest toSetPoolGroupsRequest(SetPoolGroupsRequestDTO dto) {
        PoolOuterClass.SetPoolGroupsRequest.Builder b = PoolOuterClass.SetPoolGroupsRequest.newBuilder()
                .setSaveAction(Common.SaveAction.valueOf(dto.saveAction()));
        if (dto.poolGroups() != null) {
            for (PoolGroupConfigurationDTO g : dto.poolGroups()) {
                b.addPoolGroups(toPoolGroupConfigurationProto(g));
            }
        }
        return b.build();
    }

    static PoolOuterClass.PoolGroupConfiguration toPoolGroupConfigurationProto(PoolGroupConfigurationDTO dto) {
        PoolOuterClass.PoolGroupConfiguration.Builder b = PoolOuterClass.PoolGroupConfiguration.newBuilder()
                .setName(dto.name());
        if (dto.uid() != null) b.setUid(dto.uid());
        if (dto.quota() != null) b.setQuota(PoolOuterClass.Quota.newBuilder().setValue(dto.quota()).build());
        if (dto.fixedShareRatio() != null) b.setFixedShareRatio(PoolOuterClass.FixedShareRatio.newBuilder().setValue(dto.fixedShareRatio()).build());
        if (dto.pools() != null) {
            for (PoolConfigurationDTO p : dto.pools()) {
                b.addPools(toPoolConfigurationProto(p));
            }
        }
        return b.build();
    }

    static PoolOuterClass.PoolConfiguration toPoolConfigurationProto(PoolConfigurationDTO dto) {
        PoolOuterClass.PoolConfiguration.Builder b = PoolOuterClass.PoolConfiguration.newBuilder()
                .setUrl(dto.url())
                .setUser(dto.user());
        if (dto.uid() != null) b.setUid(dto.uid());
        if (dto.password() != null) b.setPassword(dto.password());
        if (dto.enabled() != null) b.setEnabled(dto.enabled());
        return b.build();
    }
}
