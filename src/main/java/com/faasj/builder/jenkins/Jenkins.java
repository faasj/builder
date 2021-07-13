package com.faasj.builder.jenkins;
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

@Slf4j
@Component
@ConfigurationProperties
public class Jenkins {

    private JenkinsServer jenkins;

    @Value("${jenkins.url}")
    private String url;

    @Value("${jenkins.user}")
    private String user;

    @Value("${jenkins.password}")
    private String password;

    long timeToWaitMS = 1000;

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
            Thread.sleep(timeToWaitMS);
        }
        catch (InterruptedException iex) { }
    }

    public String build(String jobName) {
        log.info("Building Jenkins job with jobName: " + jobName);

        try {
            Job job = jenkins.getJob(jobName);
            log.info(jobName + " is buildable: " + job.details().isBuildable());

            QueueReference queueReference = job.build();
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
}
