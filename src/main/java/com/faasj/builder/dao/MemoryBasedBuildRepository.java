package com.faasj.builder.dao;

import com.faasj.builder.dto.BuildDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class MemoryBasedBuildRepository implements BuildRepository {

    Map<UUID, BuildDto> builds = new ConcurrentHashMap<>();
    Map<UUID, String> logs = new ConcurrentHashMap<>();

    @Override
    public void save(UUID buildId, BuildDto buildDto) {
        builds.put(buildId, buildDto);
        logs.put(buildId, String.join("", LocalDateTime.now().toString(),
                " [INFO]", " function built ", buildDto.getFunctionId().toString()));
    }

    @Override
    public void delete(UUID buildId) {
        builds.remove(buildId);
    }

    @Override
    public Optional<String> findLogs(UUID buildId) {
        return Optional.ofNullable(logs.get(buildId));
    }

    @Override
    public Set<Map.Entry<UUID, String>> findLogs() {
        return logs.entrySet();
    }

    @Override
    public Optional<BuildDto> findBuild(UUID buildId) {
        return Optional.ofNullable(builds.get(buildId));
    }
}
