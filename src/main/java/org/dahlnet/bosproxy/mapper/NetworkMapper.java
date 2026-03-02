package org.dahlnet.bosproxy.mapper;

import org.dahlnet.bosproxy.dto.*;

import braiins.bos.v1.Network;

import java.util.List;
import java.util.stream.Collectors;

public final class NetworkMapper {

    private NetworkMapper() {}

    public static GetNetworkConfigurationResponseDTO toGetNetworkConfigurationResponseDTO(Network.GetNetworkConfigurationResponse r) {
        NetworkConfigurationDTO net = r.hasNetwork() ? toNetworkConfigurationDTO(r.getNetwork()) : null;
        return new GetNetworkConfigurationResponseDTO(net);
    }

    static NetworkConfigurationDTO toNetworkConfigurationDTO(Network.NetworkConfiguration n) {
        Boolean useDhcp = null;
        StaticNetworkDTO staticConfig = null;
        switch (n.getProtocolCase()) {
            case DHCP -> useDhcp = true;
            case STATIC -> staticConfig = toStaticDTO(n.getStatic());
            default -> {}
        }
        return new NetworkConfigurationDTO(useDhcp, staticConfig, n.getHostname());
    }

    static StaticNetworkDTO toStaticDTO(Network.Static s) {
        return new StaticNetworkDTO(
                s.getAddress(),
                s.getNetmask(),
                s.getGateway(),
                s.getDnsServersList().stream().collect(Collectors.toList())
        );
    }

    public static SetNetworkConfigurationResponseDTO toSetNetworkConfigurationResponseDTO(Network.SetNetworkConfigurationResponse r) {
        NetworkConfigurationDTO net = r.hasNetwork() ? toNetworkConfigurationDTO(r.getNetwork()) : null;
        return new SetNetworkConfigurationResponseDTO(net);
    }

    public static GetNetworkInfoResponseDTO toGetNetworkInfoResponseDTO(Network.GetNetworkInfoResponse r) {
        List<IpNetworkDTO> networks = r.getNetworksList().stream()
                .map(n -> new IpNetworkDTO(n.getAddress(), n.getNetmask()))
                .collect(Collectors.toList());
        return new GetNetworkInfoResponseDTO(
                r.getName(),
                r.hasMacAddress() ? r.getMacAddress() : null,
                r.hasHostname() ? r.getHostname() : null,
                r.hasProtocol() ? r.getProtocol().name() : null,
                r.getDnsServersList().stream().collect(Collectors.toList()),
                networks,
                r.hasDefaultGateway() ? r.getDefaultGateway() : null
        );
    }

    public static Network.SetNetworkConfigurationRequest toSetNetworkConfigurationRequest(SetNetworkConfigurationRequestDTO dto) {
        Network.SetNetworkConfigurationRequest.Builder b = Network.SetNetworkConfigurationRequest.newBuilder();
        if (Boolean.TRUE.equals(dto.useDhcp())) {
            b.setDhcp(Network.Dhcp.newBuilder().build());
        } else if (dto.staticConfig() != null) {
            Network.Static.Builder sb = Network.Static.newBuilder()
                    .setAddress(dto.staticConfig().address())
                    .setNetmask(dto.staticConfig().netmask())
                    .setGateway(dto.staticConfig().gateway());
            for (String dns : dto.staticConfig().dnsServers()) {
                sb.addDnsServers(dns);
            }
            b.setStatic(sb.build());
        }
        if (dto.hostname() != null) b.setHostname(dto.hostname());
        return b.build();
    }
}
