//package com.faasj.builder.jenkins;
//
//import com.offbytwo.jenkins.model.Job;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
//* Для работы теста необходим локально работающий Jenkins и созданная в нем джоба
//* запуск build работает только при подключении через токен, а не логин, пароль
// */
//@SpringBootTest
//public class JenkinsJobTest {
//
//    @Autowired
//    Jenkins jenkins;
//    Job job;
//    Map<String, Job> jobs;
//
//    @Test
//    public void jenkinsIsRunningTest(){
//        Assertions.assertTrue(jenkins.getJenkins().isRunning());
//    }
//
//    @Test
//    public void getJenkinsJob() throws IOException {
//        job = jenkins.getJenkins().getJob("buildFunction");
//        Assertions.assertEquals("buildFunction",job.getName());
//    }
//    @Test
//    public void getAllJobs() throws IOException {
//        jobs = jenkins.getJenkins().getJobs();
//        Assertions.assertTrue(jobs.containsKey("buildFunction"));
//    }
//
//    @Test
//    public void buildJob() throws IOException {
////        String buildResult = jenkins.build("buildFunction");
////        System.out.println(buildResult);
//    }
//
//    @Test
//    public void createJob() throws IOException {
//        if(!jenkins.getJenkins().getJobs().containsKey("buildFunction")) {
//            try {
//                String sourceXml = """
//                        <?xml version="1.1" encoding="UTF-8" standalone="no"?><project>
//                            <actions/>
//                            <description/>
//                            <keepDependencies>false</keepDependencies>
//                            <properties>
//
//                            </properties>
//                            <scm class="hudson.plugins.git.GitSCM" plugin="git@4.7.2">
//                                <configVersion>2</configVersion>
//                                <userRemoteConfigs>
//                                    <hudson.plugins.git.UserRemoteConfig>
//                                        <url>https://github.com/faasj/builder.git</url>
//                                    </hudson.plugins.git.UserRemoteConfig>
//                                </userRemoteConfigs>
//                                <branches>
//                                    <hudson.plugins.git.BranchSpec>
//                                        <name>*/master</name>
//                                    </hudson.plugins.git.BranchSpec>
//                                </branches>
//                                <doGenerateSubmoduleConfigurations>false</doGenerateSubmoduleConfigurations>
//                                <submoduleCfg class="empty-list"/>
//                                <extensions/>
//                            </scm>
//                            <canRoam>true</canRoam>
//                            <disabled>false</disabled>
//                            <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>
//                            <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>
//                            <triggers/>
//                            <concurrentBuild>false</concurrentBuild>
//                            <builders/>
//                            <publishers/>
//                            <buildWrappers/>
//                        </project>
//                        """;
//                System.out.println(sourceXml);
//                jenkins.getJenkins().createJob("buildFunction", sourceXml);
//            } catch (IOException e) {
//                System.out.println("Failed to search Jenkins jobs");
//                e.printStackTrace();
//            }
//        } else System.out.println("Job already exist!");
//    }
//}
//
