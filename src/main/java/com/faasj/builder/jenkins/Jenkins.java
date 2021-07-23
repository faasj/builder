package com.faasj.builder.jenkins;
import com.faasj.builder.dto.FunctionBuildDto;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.QueueReference;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class Jenkins {

    private JenkinsServer jenkins;

    private final Map<Job, String> functionsJobs = new ConcurrentHashMap<>();

    @Value("${jenkins.url}")
    private String url;

    @Value("${jenkins.user}")
    private String user;

    @Value("${jenkins.password}")
    private String password;

    @Value("${jenkins.requirements}")
    private String requirements;

    @Value("${jenkins.dockerfile}")
    private String dockerfile;

    static final long TIME_TO_WAIT_MS = 1000;

    public JenkinsServer getJenkins() {

        int numRetries = 3;

        try {
            jenkins = new JenkinsServer(new URI(url), user, password);
        } catch (URISyntaxException e) {
            log.error("Jenkins URI syntax exception");
            return null;
        }

        while (!jenkins.isRunning()) {
            waitUntilNextTry();
            log.error("retry connection: " + numRetries);
            numRetries--;
            if(numRetries <= 0) {
                log.error("Failed. Jenkins not started.");
                return null;
            }
        }
        return jenkins;
    }

    public void waitUntilNextTry()
    {
        try {
            Thread.sleep(TIME_TO_WAIT_MS);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String build(String jobName, FunctionBuildDto functionBuildDto) {
        log.info("Building Jenkins job with jobName: " + jobName);

        Map<String, String> environments = Map.of("image", functionBuildDto.getImage(),
                "code", functionBuildDto.getCode(),
                "requirements", requirements,
                "dockerfile", dockerfile);

        try {
            Job job = jenkins.getJob(jobName);
            log.info(jobName + " is buildable: " + job.details().isBuildable());

            job.build(environments, true);

            functionsJobs.put(job, functionBuildDto.getName());

            log.debug(job.toString());
            int waitFor = 0;
            while (job.details().isInQueue()) {
                waitFor++;
                log.info("Job in queue");
                Thread.sleep(5000);
                if (waitFor > 4) return "Job is built successfully, but is in Queue";
            }
        } catch (Exception e) {
            log.info("Failed to build Jenkins job with jobName: " + jobName);
            e.printStackTrace();
        }
        return "Build job complete";
    }

    public void createFreeStyleJob(String jobName) {
        try {
            jenkins.createJob(null, jobName, "freestyle.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("Created freestyle job "+ jobName);
    }

    public String getBuildStatus(String functionName) {
        return functionsJobs.entrySet().stream()
                .filter(entry -> entry.getValue().equals(functionName))
                .map(entry -> {
                    try {

                        return entry.getKey().details().isInQueue() ?
                                String.format("Function \"%s\" id building", functionName) :
                                String.format("Function \"%s\" built!", functionName);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return e.getMessage();
                    }
                })
                .findFirst()
                .orElseThrow(NoSuchElementException::new);
    }
}
