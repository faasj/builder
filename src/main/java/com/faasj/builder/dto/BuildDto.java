package com.faasj.builder.dto;

import com.faasj.builder.util.State;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class BuildDto {

    private UUID buildId;
    private UUID functionId;
    private State state;
    private Map<String, String> properties;
    private LocalDateTime created;
    private LocalDateTime updated;
}
