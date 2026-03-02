package org.dahlnet.bosproxy.dto;

import java.util.List;

public record ListTargetProfilesResponseDTO(List<Object> powerTargetProfiles, List<Object> hashrateTargetProfiles) {}
