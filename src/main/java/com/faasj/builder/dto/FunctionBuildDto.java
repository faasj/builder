package com.faasj.builder.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class FunctionBuildDto {

    UUID functionId;
    String code;
    String image;
}
