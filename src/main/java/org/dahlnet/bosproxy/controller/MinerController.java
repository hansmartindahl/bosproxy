package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.*;
import org.dahlnet.bosproxy.mapper.MinerMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.Common;
import braiins.bos.v1.Miner;
import braiins.bos.v1.MinerServiceGrpc.MinerServiceBlockingStub;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Iterator;

@RestController
@RequestMapping("/miner")
public class MinerController {
    private final MinerServiceBlockingStub minerStub;

    public MinerController(MinerServiceBlockingStub minerStub) {
        this.minerStub = minerStub;
    }

    /** Streaming API: returns a snapshot (first response from stream). */
    @GetMapping("/get-miner-status")
    public ResponseEntity<GetMinerStatusResponseDTO> getMinerStatus() {
        Iterator<Miner.GetMinerStatusResponse> stream = minerStub.getMinerStatus(
                Miner.GetMinerStatusRequest.getDefaultInstance());
        Miner.GetMinerStatusResponse snapshot = stream.hasNext() ? stream.next() : null;
        if (snapshot == null) {
            return ResponseEntity.ok(new GetMinerStatusResponseDTO("UNKNOWN"));
        }
        return ResponseEntity.ok(new GetMinerStatusResponseDTO(snapshot.getStatus().name()));
    }

    @GetMapping("/get-miner-details")
    public ResponseEntity<GetMinerDetailsResponseDTO> getMinerDetails() {
        Miner.GetMinerDetailsResponse r = minerStub.getMinerDetails(
                Miner.GetMinerDetailsRequest.getDefaultInstance());
        return ResponseEntity.ok(MinerMapper.toGetMinerDetailsResponseDTO(r));
    }

    @GetMapping("/get-miner-stats")
    public ResponseEntity<GetMinerStatsResponseDTO> getMinerStats() {
        Miner.GetMinerStatsResponse r = minerStub.getMinerStats(
                Miner.GetMinerStatsRequest.getDefaultInstance());
        return ResponseEntity.ok(MinerMapper.toGetMinerStatsResponseDTO(r));
    }

    @GetMapping("/get-errors")
    public ResponseEntity<GetErrorsResponseDTO> getErrors() {
        Miner.GetErrorsResponse r = minerStub.getErrors(Miner.GetErrorsRequest.getDefaultInstance());
        return ResponseEntity.ok(MinerMapper.toGetErrorsResponseDTO(r));
    }

    @GetMapping("/get-hashboards")
    public ResponseEntity<GetHashboardsResponseDTO> getHashboards() {
        Miner.GetHashboardsResponse r = minerStub.getHashboards(Miner.GetHashboardsRequest.getDefaultInstance());
        return ResponseEntity.ok(MinerMapper.toGetHashboardsResponseDTO(r));
    }

    /** Streaming API: returns full archive as single binary (all chunks concatenated). */
    @GetMapping("/get-support-archive")
    public ResponseEntity<byte[]> getSupportArchive(
            @RequestParam(defaultValue = "SUPPORT_ARCHIVE_FORMAT_ZIP") String format) {
        Miner.SupportArchiveFormat fmt = Miner.SupportArchiveFormat.valueOf(format);
        Miner.GetSupportArchiveRequest req = Miner.GetSupportArchiveRequest.newBuilder().setFormat(fmt).build();
        Iterator<Miner.GetSupportArchiveResponse> stream = minerStub.getSupportArchive(req);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (stream.hasNext()) {
            try {
                out.write(stream.next().getChunkData().toByteArray());
            } catch (java.io.IOException e) {
                throw new RuntimeException(e);
            }
        }
        String contentType = format.toUpperCase().contains("ZIP") ? "application/zip" : "application/octet-stream";
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"support-archive\"")
                .body(out.toByteArray());
    }

    @PostMapping("/enable-hashboards")
    public ResponseEntity<List<HashboardEnableStateDTO>> enableHashboards(
            @RequestBody EnableHashboardsRequestDTO body) {
        Common.SaveAction saveAction = Common.SaveAction.valueOf(body.saveAction());
        Miner.EnableHashboardsRequest req = Miner.EnableHashboardsRequest.newBuilder()
                .setSaveAction(saveAction)
                .addAllHashboardIds(body.hashboardIds())
                .build();
        Miner.EnableHashboardsResponse r = minerStub.enableHashboards(req);
        return ResponseEntity.ok(MinerMapper.toHashboardEnableStateDTOs(r.getHashboardsList()));
    }

    @PostMapping("/disable-hashboards")
    public ResponseEntity<List<HashboardEnableStateDTO>> disableHashboards(
            @RequestBody EnableHashboardsRequestDTO body) {
        Common.SaveAction saveAction = Common.SaveAction.valueOf(body.saveAction());
        Miner.DisableHashboardsRequest req = Miner.DisableHashboardsRequest.newBuilder()
                .setSaveAction(saveAction)
                .addAllHashboardIds(body.hashboardIds())
                .build();
        Miner.DisableHashboardsResponse r = minerStub.disableHashboards(req);
        return ResponseEntity.ok(MinerMapper.toHashboardEnableStateDTOs(r.getHashboardsList()));
    }
}
