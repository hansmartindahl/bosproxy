package org.dahlnet.bosproxy.dto;

import java.util.List;

public record GetErrorsResponseDTO(List<MinerErrorDTO> errors) {}
