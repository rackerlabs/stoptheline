package com.rackspace.jenkins.stoptheline.client;

import com.offbytwo.jenkins.model.Job;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;

public class JenkinsClientTest {


    @Test
    public void testRealJenkins() throws IOException, URISyntaxException {
        JenkinsClient client = new JenkinsClientImpl("http://jenkins.inova.dfw1.ci.rackspace.net");


        Map<String, Job> jobs = client.getAllJobs();

    }
}
