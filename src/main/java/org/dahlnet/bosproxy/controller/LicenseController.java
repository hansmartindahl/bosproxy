package org.dahlnet.bosproxy.controller;

import org.dahlnet.bosproxy.dto.GetLicenseStateResponseDTO;
import org.dahlnet.bosproxy.mapper.LicenseMapper;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import braiins.bos.v1.License;
import braiins.bos.v1.LicenseServiceGrpc.LicenseServiceBlockingStub;

@RestController
@RequestMapping("/license")
public class LicenseController {
    private final LicenseServiceBlockingStub licenseStub;

    public LicenseController(LicenseServiceBlockingStub licenseStub) {
        this.licenseStub = licenseStub;
    }

    @GetMapping(value = "/get-license-state", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<GetLicenseStateResponseDTO> getLicenseState() {
        License.GetLicenseStateResponse r = licenseStub.getLicenseState(
                License.GetLicenseStateRequest.getDefaultInstance());
        return ResponseEntity.ok(LicenseMapper.toGetLicenseStateResponseDTO(r));
    }
}
