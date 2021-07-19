package com.faasj.builder.dao;

import com.faasj.builder.dto.BuildDto;

import java.util.*;

public interface BuildRepository {

    void save(UUID buildId, BuildDto buildDto);

    void delete(UUID buildId);

    Optional<String> findLogs(UUID buildId);

    Set<Map.Entry<UUID, String>> findLogs();

    Optional<BuildDto> findBuild(UUID buildId);
}
