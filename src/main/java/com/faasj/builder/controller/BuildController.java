package com.faasj.builder.controller;

import com.faasj.builder.dto.BuildDto;
import com.faasj.builder.dto.FunctionBuildDto;
import com.faasj.builder.service.BuildService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class BuildController {

    private final BuildService service;

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildDto> startBuild(@RequestBody FunctionBuildDto function) {
        BuildDto buildDto = service.startBuild(function);

        return new ResponseEntity<>(buildDto, HttpStatus.OK);
    }

    @GetMapping(value = "{buildId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<BuildDto> getBuild(@PathVariable UUID buildId) {
        BuildDto buildDto = service.getBuild(buildId);

        return Objects.nonNull(buildDto) ?
                new ResponseEntity<>(buildDto, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "logs/{buildId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBuildLogs(@PathVariable UUID buildId) {
        String log = service.getBuildLogs(buildId);

        return Objects.nonNull(log) ?
                new ResponseEntity<>(log, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = "logs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getBuildLogs() {
        String log = service.getBuildLogs();

        return Objects.nonNull(log) ?
                new ResponseEntity<>(log, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("{buildId}")
    public ResponseEntity<HttpStatus> deleteBuild(@PathVariable UUID buildId) {
        service.deleteBuild(buildId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
