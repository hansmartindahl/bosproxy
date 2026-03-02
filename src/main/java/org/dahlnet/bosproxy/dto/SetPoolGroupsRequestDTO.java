package org.dahlnet.bosproxy.dto;

import java.util.List;

public record SetPoolGroupsRequestDTO(String saveAction, List<PoolGroupConfigurationDTO> poolGroups) {}
