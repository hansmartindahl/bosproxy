package org.dahlnet.bosproxy.dto;

import java.util.List;

public record EnableHashboardsRequestDTO(String saveAction, List<String> hashboardIds) {}
