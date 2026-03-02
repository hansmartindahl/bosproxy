package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.*;
import org.dahlnet.bosproxy.mapper.PoolMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.PoolOuterClass;
import braiins.bos.v1.PoolServiceGrpc.PoolServiceBlockingStub;

@RestController
@RequestMapping("/pool")
public class PoolController {
    private final PoolServiceBlockingStub poolStub;

    public PoolController(PoolServiceBlockingStub poolStub) {
        this.poolStub = poolStub;
    }

    @GetMapping(value = "/get-pool-groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetPoolGroupsResponseDTO> getPoolGroups() {
        PoolOuterClass.GetPoolGroupsResponse r = poolStub.getPoolGroups(
                PoolOuterClass.GetPoolGroupsRequest.getDefaultInstance());
        return ResponseEntity.ok(PoolMapper.toGetPoolGroupsResponseDTO(r));
    }

    @PostMapping(value = "/create-pool-group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CreatePoolGroupResponseDTO> createPoolGroup(@RequestBody CreatePoolGroupRequestDTO body) {
        PoolOuterClass.CreatePoolGroupResponse r = poolStub.createPoolGroup(PoolMapper.toCreatePoolGroupRequest(body));
        return ResponseEntity.ok(PoolMapper.toCreatePoolGroupResponseDTO(r));
    }

    @PostMapping(value = "/update-pool-group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UpdatePoolGroupResponseDTO> updatePoolGroup(@RequestBody UpdatePoolGroupRequestDTO body) {
        PoolOuterClass.UpdatePoolGroupResponse r = poolStub.updatePoolGroup(PoolMapper.toUpdatePoolGroupRequest(body));
        return ResponseEntity.ok(PoolMapper.toUpdatePoolGroupResponseDTO(r));
    }

    @PostMapping(value = "/remove-pool-group", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removePoolGroup(@RequestBody RemovePoolGroupRequestDTO body) {
        poolStub.removePoolGroup(PoolMapper.toRemovePoolGroupRequest(body));
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/set-pool-groups", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SetPoolGroupsResponseDTO> setPoolGroups(@RequestBody SetPoolGroupsRequestDTO body) {
        PoolOuterClass.SetPoolGroupsResponse r = poolStub.setPoolGroups(PoolMapper.toSetPoolGroupsRequest(body));
        return ResponseEntity.ok(PoolMapper.toSetPoolGroupsResponseDTO(r));
    }
}
