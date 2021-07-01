package com.faasj.builder.jenkins;
import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueReference;
import lombok.extern.slf4j.Slf4j;
import java.net.URI;

@Slf4j
public class Jenkins {

    private JenkinsServer jenkins;
    private JenkinsHttpClient jenkinsHttpClient;

    private static final String JENKINS_URL = "http://localhost:8080/";
    private static final String JENKINS_USER = "admin";
    private static final String JENKINS_PASSWORD = "11011d22461beb771fdf575d59d77cdf30";

    public void init(){
        try{
            jenkins = new JenkinsServer(new URI(JENKINS_URL), JENKINS_USER, JENKINS_PASSWORD);
            jenkinsHttpClient = new JenkinsHttpClient(new URI(JENKINS_URL), JENKINS_USER, JENKINS_PASSWORD);
        } catch (Exception e){
            log.error("Failed to Connect to Jenkins Instance");
            e.printStackTrace();
        }
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

    public JenkinsServer getJenkins() {
        return jenkins;
    }
}
