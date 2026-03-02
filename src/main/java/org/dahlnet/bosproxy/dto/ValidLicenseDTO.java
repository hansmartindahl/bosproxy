package org.dahlnet.bosproxy.dto;

public record ValidLicenseDTO(String type, String contractName, int timeToRestricted, int devFeeBsp) {}
