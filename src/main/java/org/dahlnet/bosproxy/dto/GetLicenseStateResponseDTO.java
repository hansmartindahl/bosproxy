package org.dahlnet.bosproxy.dto;

public record GetLicenseStateResponseDTO(
        NoneLicenseDTO none,
        Object limited,
        ValidLicenseDTO valid,
        ExpiredLicenseDTO expired
) {}
