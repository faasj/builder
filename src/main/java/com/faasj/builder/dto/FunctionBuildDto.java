package com.faasj.builder.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FunctionBuildDto {

    UUID functionId;
    String code;
    String image;
}
