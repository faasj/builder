package com.faasj.builder.service;

import com.faasj.builder.dao.BuildRepository;
import com.faasj.builder.dto.BuildDto;
import com.faasj.builder.dto.FunctionBuildDto;
import com.faasj.builder.util.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BuildServiceImpl implements BuildService {

    private final BuildRepository repository;

    @Override
    public BuildDto startBuild(FunctionBuildDto function) {
        BuildDto build = BuildDto.builder()
                .functionId(function.getFunctionId())
                .buildId(UUID.randomUUID())
                .state(State.BUILDING)
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .properties(Map.of("test", "test"))
                .build();

        repository.save(build.getBuildId(), build);

        return build;
    }

    @Override
    public BuildDto getBuild(UUID buildId) {
        BuildDto buildDto =
                repository.findBuild(buildId).orElseThrow(() -> new NoSuchElementException("No such element " + buildId));
        buildDto.setState(State.BUILT);

        return buildDto;
    }

    @Override
    public String getBuildLogs(UUID buildId) {
        return repository.findLogs(buildId).orElseThrow(() -> new NoSuchElementException("No such logs " + buildId));
    }

    @Override
    public String getBuildLogs() {
        return repository.findLogs().stream()
                .map(entry -> String.join("", entry.getKey().toString(), " ", entry.getValue()))
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void deleteBuild(UUID buildId) {
        repository.delete(buildId);
    }
}
