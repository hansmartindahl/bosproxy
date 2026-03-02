package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.Actions;
import braiins.bos.v1.ActionsServiceGrpc.ActionsServiceBlockingStub;

@RestController
@RequestMapping("/actions")
public class ActionsController {
    private final ActionsServiceBlockingStub actionsStub;

    public ActionsController(ActionsServiceBlockingStub actionsStub) {
        this.actionsStub = actionsStub;
    }

    @PostMapping("/start")
    public ResponseEntity<StartResponseDTO> start() {
        Actions.StartResponse r = actionsStub.start(Actions.StartRequest.getDefaultInstance());
        return ResponseEntity.ok(new StartResponseDTO(r.getAlreadyRunning()));
    }

    @PostMapping("/stop")
    public ResponseEntity<StopResponseDTO> stop() {
        Actions.StopResponse r = actionsStub.stop(Actions.StopRequest.getDefaultInstance());
        return ResponseEntity.ok(new StopResponseDTO(r.getAlreadyStopped()));
    }

    @PostMapping("/pause-mining")
    public ResponseEntity<PauseMiningResponseDTO> pauseMining() {
        Actions.PauseMiningResponse r = actionsStub.pauseMining(Actions.PauseMiningRequest.getDefaultInstance());
        return ResponseEntity.ok(new PauseMiningResponseDTO(r.getAlreadyPaused()));
    }

    @PostMapping("/resume-mining")
    public ResponseEntity<ResumeMiningResponseDTO> resumeMining() {
        Actions.ResumeMiningResponse r = actionsStub.resumeMining(Actions.ResumeMiningRequest.getDefaultInstance());
        return ResponseEntity.ok(new ResumeMiningResponseDTO(r.getAlreadyMining()));
    }

    @PostMapping("/restart")
    public ResponseEntity<RestartResponseDTO> restart() {
        Actions.RestartResponse r = actionsStub.restart(Actions.RestartRequest.getDefaultInstance());
        return ResponseEntity.ok(new RestartResponseDTO(r.getAlreadyRunning()));
    }

    @PostMapping("/reboot")
    public ResponseEntity<Void> reboot() {
        actionsStub.reboot(Actions.RebootRequest.getDefaultInstance());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/set-locate-device-status")
    public ResponseEntity<LocateDeviceResponseDTO> setLocateDeviceStatus(@RequestBody LocateDeviceRequestDTO body) {
        Actions.SetLocateDeviceStatusRequest req = Actions.SetLocateDeviceStatusRequest.newBuilder()
                .setEnable(body.enable())
                .build();
        Actions.LocateDeviceStatusResponse r = actionsStub.setLocateDeviceStatus(req);
        return ResponseEntity.ok(new LocateDeviceResponseDTO(r.getEnabled()));
    }

    @GetMapping("/get-locate-device-status")
    public ResponseEntity<LocateDeviceResponseDTO> getLocateDeviceStatus() {
        Actions.LocateDeviceStatusResponse r = actionsStub.getLocateDeviceStatus(
                Actions.GetLocateDeviceStatusRequest.getDefaultInstance());
        return ResponseEntity.ok(new LocateDeviceResponseDTO(r.getEnabled()));
    }
}
