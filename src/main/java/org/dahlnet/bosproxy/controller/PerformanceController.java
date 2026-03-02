package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.*;
import org.dahlnet.bosproxy.mapper.PerformanceMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.Performance;
import braiins.bos.v1.PerformanceServiceGrpc.PerformanceServiceBlockingStub;

@RestController
@RequestMapping("/performance")
public class PerformanceController {
    private final PerformanceServiceBlockingStub performanceStub;

    public PerformanceController(PerformanceServiceBlockingStub performanceStub) {
        this.performanceStub = performanceStub;
    }

    @GetMapping(value = "/get-tuner-state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetTunerStateResponseDTO> getTunerState() {
        Performance.GetTunerStateResponse r = performanceStub.getTunerState(
                Performance.GetTunerStateRequest.getDefaultInstance());
        return ResponseEntity.ok(PerformanceMapper.toGetTunerStateResponseDTO(r));
    }

    @GetMapping(value = "/list-target-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ListTargetProfilesResponseDTO> listTargetProfiles() {
        Performance.ListTargetProfilesResponse r = performanceStub.listTargetProfiles(
                Performance.ListTargetProfilesRequest.getDefaultInstance());
        return ResponseEntity.ok(PerformanceMapper.toListTargetProfilesResponseDTO(r));
    }

    @PostMapping(value = "/set-default-power-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetPowerTargetResponseDTO> setDefaultPowerTarget(@RequestBody SetPowerTargetRequestDTO body) {
        Performance.SetPowerTargetResponse r = performanceStub.setDefaultPowerTarget(PerformanceMapper.toSetDefaultPowerTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetPowerTargetResponseDTO(r));
    }

    @PostMapping(value = "/set-power-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetPowerTargetResponseDTO> setPowerTarget(@RequestBody SetPowerTargetRequestDTO body) {
        Performance.SetPowerTargetResponse r = performanceStub.setPowerTarget(PerformanceMapper.toSetPowerTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetPowerTargetResponseDTO(r));
    }

    @PostMapping(value = "/increment-power-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetPowerTargetResponseDTO> incrementPowerTarget(@RequestBody SetPowerTargetRequestDTO body) {
        Performance.SetPowerTargetResponse r = performanceStub.incrementPowerTarget(PerformanceMapper.toIncrementPowerTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetPowerTargetResponseDTO(r));
    }

    @PostMapping(value = "/decrement-power-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetPowerTargetResponseDTO> decrementPowerTarget(@RequestBody SetPowerTargetRequestDTO body) {
        Performance.SetPowerTargetResponse r = performanceStub.decrementPowerTarget(PerformanceMapper.toDecrementPowerTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetPowerTargetResponseDTO(r));
    }

    @PostMapping(value = "/set-default-hashrate-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetHashrateTargetResponseDTO> setDefaultHashrateTarget(@RequestBody SetHashrateTargetRequestDTO body) {
        Performance.SetHashrateTargetResponse r = performanceStub.setDefaultHashrateTarget(PerformanceMapper.toSetDefaultHashrateTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetHashrateTargetResponseDTO(r));
    }

    @PostMapping(value = "/set-hashrate-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetHashrateTargetResponseDTO> setHashrateTarget(@RequestBody SetHashrateTargetRequestDTO body) {
        Performance.SetHashrateTargetResponse r = performanceStub.setHashrateTarget(PerformanceMapper.toSetHashrateTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetHashrateTargetResponseDTO(r));
    }

    @PostMapping(value = "/increment-hashrate-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetHashrateTargetResponseDTO> incrementHashrateTarget(@RequestBody SetHashrateTargetRequestDTO body) {
        Performance.SetHashrateTargetResponse r = performanceStub.incrementHashrateTarget(PerformanceMapper.toIncrementHashrateTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetHashrateTargetResponseDTO(r));
    }

    @PostMapping(value = "/decrement-hashrate-target", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetHashrateTargetResponseDTO> decrementHashrateTarget(@RequestBody SetHashrateTargetRequestDTO body) {
        Performance.SetHashrateTargetResponse r = performanceStub.decrementHashrateTarget(PerformanceMapper.toDecrementHashrateTargetRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetHashrateTargetResponseDTO(r));
    }

    @PostMapping(value = "/set-dps", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetDPSResponseDTO> setDPS(@RequestBody SetDPSRequestDTO body) {
        Performance.SetDPSResponse r = performanceStub.setDPS(PerformanceMapper.toSetDPSRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toSetDPSResponseDTO(r));
    }

    @PostMapping(value = "/set-performance-mode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PerformanceModeDTO> setPerformanceMode(@RequestBody SetPerformanceModeRequestDTO body) {
        Performance.PerformanceMode r = performanceStub.setPerformanceMode(PerformanceMapper.toSetPerformanceModeRequest(body));
        return ResponseEntity.ok(PerformanceMapper.toPerformanceModeDTO(r));
    }

    @GetMapping(value = "/get-active-performance-mode", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PerformanceModeDTO> getActivePerformanceMode() {
        Performance.PerformanceMode r = performanceStub.getActivePerformanceMode(
                Performance.GetPerformanceModeRequest.getDefaultInstance());
        return ResponseEntity.ok(PerformanceMapper.toPerformanceModeDTO(r));
    }

    @PostMapping(value = "/remove-tuned-profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeTunedProfiles() {
        performanceStub.removeTunedProfiles(Performance.RemoveTunedProfilesRequest.getDefaultInstance());
        return ResponseEntity.ok().build();
    }
}
