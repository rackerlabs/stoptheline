package com.rackspace.jenkins.stoptheline.client;


import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;

import java.io.IOException;
import java.util.Map;

/**
 * Interface file to the jenkins-client which a java client for Jenkins API
 */
public interface JenkinsClient {

    public Map<String, Job> getAllJobs();

    public JobWithDetails getJobDetails(String jobName)  throws IOException;

    public boolean isJobSuccessful(JobWithDetails job);

    public boolean isAllJobsSuccessful(Map<String, Job> jobs)  throws IOException ;

    public String getCulpritName(String path);

    }
