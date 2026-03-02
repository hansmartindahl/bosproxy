package org.dahlnet.bosproxy.dto;

import java.util.List;

public record GetConstraintsResponseDTO(
        String tunerConstraints,
        String coolingConstraints,
        String dpsConstraints,
        List<String> hashboardIds
) {}
