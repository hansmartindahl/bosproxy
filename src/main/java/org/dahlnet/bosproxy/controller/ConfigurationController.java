package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.GetConstraintsResponseDTO;
import org.dahlnet.bosproxy.dto.GetMinerConfigurationResponseDTO;
import org.dahlnet.bosproxy.mapper.ConfigurationMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.Configuration;
import braiins.bos.v1.ConfigurationServiceGrpc.ConfigurationServiceBlockingStub;

@RestController
@RequestMapping("/configuration")
public class ConfigurationController {
    private final ConfigurationServiceBlockingStub configurationStub;

    public ConfigurationController(ConfigurationServiceBlockingStub configurationStub) {
        this.configurationStub = configurationStub;
    }

    @GetMapping(value = "/get-miner-configuration", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetMinerConfigurationResponseDTO> getMinerConfiguration() {
        Configuration.GetMinerConfigurationResponse r = configurationStub.getMinerConfiguration(
                Configuration.GetMinerConfigurationRequest.getDefaultInstance());
        return ResponseEntity.ok(ConfigurationMapper.toGetMinerConfigurationResponseDTO(r));
    }

    @GetMapping(value = "/get-constraints", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetConstraintsResponseDTO> getConstraints() {
        Configuration.GetConstraintsResponse r = configurationStub.getConstraints(
                Configuration.GetConstraintsRequest.getDefaultInstance());
        return ResponseEntity.ok(ConfigurationMapper.toGetConstraintsResponseDTO(r));
    }
}
