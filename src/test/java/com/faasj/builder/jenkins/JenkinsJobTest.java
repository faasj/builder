package com.faasj.builder.jenkins;

import com.offbytwo.jenkins.model.Job;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Map;

/**
* Для работы теста необходим локально работающий Jenkins и созданная в нем джоба
* запуск build работает только при подключении через токен, а не логин, пароль
 */

public class JenkinsJobTest {
    Jenkins jenkins;
    Job job;

    @Test
    public void getAllJobs(){

        jenkins = new Jenkins();
        jenkins.init();

        try {
            job = jenkins.getJenkins().getJob("buildFunction");
            String buildResult = jenkins.build(job.getName());

            int lastBuildNumber = job.details().getLastBuild().getNumber();
            System.out.println("#" + lastBuildNumber + " " + buildResult);

        } catch (IOException e) {
            System.out.println("Failed to search Jenkins jobs");
            e.printStackTrace();
        }
    }

}

