package com.rackspace.jenkins.stoptheline.client;

import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.JenkinsServer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 */
public class JenkinsClientImpl implements JenkinsClient{

    public static String  jenkinsURI;
    private JenkinsServer jenkins;

    public JenkinsClientImpl(String jenkinsURI) throws URISyntaxException,IOException{

        this.jenkinsURI=jenkinsURI;
        jenkins=null;

        if(jenkinsURI!=null && !jenkinsURI.isEmpty()){
            jenkins = new JenkinsServer(new URI(jenkinsURI));
        }

    }


    public Map<String, Job> getAllJobs(){

        Map<String, Job> jobs = new HashMap<String, Job>();
        try {
            jobs.putAll(jenkins.getJobs());
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.

        }

        return jobs;
    }

    public JobWithDetails getJobDetails(String jobName) throws IOException {
        if(jobName!=null && !jobName.isEmpty())  {
            return jenkins.getJob(jobName).details();
        }else{
            return null;
        }



    }

    public boolean isJobSuccessful(JobWithDetails job){
        if(job.getLastUnsuccessfulBuild().getNumber() == job.getLastBuild().getNumber()) {
            return false;
        }else{
            return true;
        }


    }

    public boolean isAllJobsSuccessful(Map<String, Job> jobs)  throws IOException{
        for(Map.Entry<String, Job> job: jobs.entrySet()){
             if(!isJobSuccessful(getJobDetails(job.getKey()))){
                return false;
             }
        }

        return true;
    }



}
