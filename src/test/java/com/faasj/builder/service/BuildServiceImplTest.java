package com.faasj.builder.service;

import com.faasj.builder.dto.BuildDto;
import com.faasj.builder.dto.FunctionBuildDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BuildServiceImplTest {

    @Autowired
    private BuildService buildService;
    private BuildDto buildDto;

    private static FunctionBuildDto functionBuildDto;

    @BeforeAll
    static void setUp() {
        functionBuildDto = new FunctionBuildDto(UUID.fromString("f28f60d9-60f7-4e13-9602-1a3fc4298d0c"), "code", "image");
    }

    @BeforeEach
    void set() {
        buildDto = buildService.startBuild(functionBuildDto);
    }

    @Test
    void test_startBuild_functionId_equals() {
        assertEquals(functionBuildDto.getFunctionId(), buildDto.getFunctionId());
    }

    @Test
    void test_getBuild_expected_same_buildDto() {
        assertEquals(buildDto, buildService.getBuild(buildDto.getBuildId()));
    }

    @Test
    void test_getBuildLogs_logs_is_not_null() {
        assertNotNull(buildService.getBuildLogs());
    }

    @Test
    void test_getBuildLogs_with_parameters_logs_is_not_null() {
        assertNotNull(buildService.getBuildLogs(buildDto.getBuildId()));
    }

    @Test
    void test_deleteBuild_after_deleting_throw_exception_noSuchElementExc() {
        buildService.deleteBuild(buildDto.getBuildId());
        assertThrows(NoSuchElementException.class, () -> buildService.getBuild(buildDto.getBuildId()));
    }
}
