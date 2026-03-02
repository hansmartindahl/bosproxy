package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.*;
import org.dahlnet.bosproxy.mapper.NetworkMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.Network;
import braiins.bos.v1.NetworkServiceGrpc.NetworkServiceBlockingStub;

@RestController
@RequestMapping("/network")
public class NetworkController {
    private final NetworkServiceBlockingStub networkStub;

    public NetworkController(NetworkServiceBlockingStub networkStub) {
        this.networkStub = networkStub;
    }

    @GetMapping(value = "/get-network-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetNetworkConfigurationResponseDTO> getNetworkConfiguration() {
        Network.GetNetworkConfigurationResponse r = networkStub.getNetworkConfiguration(
                Network.GetNetworkConfigurationRequest.getDefaultInstance());
        return ResponseEntity.ok(NetworkMapper.toGetNetworkConfigurationResponseDTO(r));
    }

    @PostMapping(value = "/set-network-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetNetworkConfigurationResponseDTO> setNetworkConfiguration(@RequestBody SetNetworkConfigurationRequestDTO body) {
        Network.SetNetworkConfigurationResponse r = networkStub.setNetworkConfiguration(NetworkMapper.toSetNetworkConfigurationRequest(body));
        return ResponseEntity.ok(NetworkMapper.toSetNetworkConfigurationResponseDTO(r));
    }

    @GetMapping(value = "/get-network-info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetNetworkInfoResponseDTO> getNetworkInfo() {
        Network.GetNetworkInfoResponse r = networkStub.getNetworkInfo(Network.GetNetworkInfoRequest.getDefaultInstance());
        return ResponseEntity.ok(NetworkMapper.toGetNetworkInfoResponseDTO(r));
    }
}
