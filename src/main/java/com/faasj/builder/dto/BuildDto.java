package com.faasj.builder.dto;

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
    private String state;
    private Map<String, String> properties;
    private LocalDateTime created;
    private LocalDateTime updated;
}
