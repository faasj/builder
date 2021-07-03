package com.faasj.builder.controller;

import com.faasj.builder.dto.BuildDto;
import com.faasj.builder.dto.FunctionBuildDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/")
public class BuildController {

    Map<UUID, BuildDto> builds = new HashMap<>();

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildDto> startBuild(@RequestBody FunctionBuildDto function) {
        BuildDto build = BuildDto.builder()
                .functionId(function.getFunctionId())
                .buildId(UUID.randomUUID())
                .state("Built")
                .created(LocalDateTime.now())
                .updated(LocalDateTime.now())
                .properties(Map.of("test", "test"))
                .build();

        builds.put(build.getBuildId(), build);

        return new ResponseEntity<>(build, HttpStatus.OK);
    }

    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildDto> getBuild(@PathVariable UUID id) {
        return new ResponseEntity<>(builds.get(id), HttpStatus.OK);
    }

    @GetMapping(value = "logs/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBuildLogs(@PathVariable UUID id) {
        return new ResponseEntity<>(id + " [INFO] logs", HttpStatus.OK);
    }
}
