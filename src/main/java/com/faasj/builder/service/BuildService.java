package com.faasj.builder.service;

import com.faasj.builder.dto.BuildDto;
import com.faasj.builder.dto.FunctionBuildDto;

import java.util.UUID;

public interface BuildService {

    BuildDto startBuild(FunctionBuildDto function);

    BuildDto getBuild(UUID buildId);

    String getBuildLogs(UUID buildId);

    String getBuildLogs();

    void deleteBuild(UUID buildId);
}
