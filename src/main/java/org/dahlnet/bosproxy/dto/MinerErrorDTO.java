package org.dahlnet.bosproxy.dto;

import java.util.List;

public record MinerErrorDTO(
        String timestamp,
        String message,
        List<ErrorCodeDTO> errorCodes,
        List<ComponentDTO> components
) {}
