package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.GetCoolingStateResponseDTO;
import org.dahlnet.bosproxy.dto.SetCoolingModeRequestDTO;
import org.dahlnet.bosproxy.dto.SetCoolingModeResponseDTO;
import org.dahlnet.bosproxy.mapper.CoolingMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.Cooling;
import braiins.bos.v1.CoolingServiceGrpc.CoolingServiceBlockingStub;

@RestController
@RequestMapping("/cooling")
public class CoolingController {
    private final CoolingServiceBlockingStub coolingStub;

    public CoolingController(CoolingServiceBlockingStub coolingStub) {
        this.coolingStub = coolingStub;
    }

    @GetMapping(value = "/get-cooling-state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetCoolingStateResponseDTO> getCoolingState() {
        Cooling.GetCoolingStateResponse r = coolingStub.getCoolingState(
                Cooling.GetCoolingStateRequest.getDefaultInstance());
        return ResponseEntity.ok(CoolingMapper.toGetCoolingStateResponseDTO(r));
    }

    @PostMapping(value = "/set-cooling-mode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetCoolingModeResponseDTO> setCoolingMode(@RequestBody SetCoolingModeRequestDTO body) {
        Cooling.SetCoolingModeResponse r = coolingStub.setCoolingMode(CoolingMapper.toSetCoolingModeRequest(body));
        return ResponseEntity.ok(CoolingMapper.toSetCoolingModeResponseDTO(r));
    }
}
