package com.rackspace.jenkins.stoptheline.client;

import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.JenkinsServer;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 */
public class JenkinsClientImpl implements JenkinsClient{

    public static String  jenkinsURI;
    public static Map<String, Job> jobs;

    public JenkinsClientImpl(String jenkinsURI) throws URISyntaxException,IOException{

        this.jenkinsURI=jenkinsURI;
        JenkinsServer jenkins=null;

        if(jenkinsURI!=null && !jenkinsURI.isEmpty()){
            jenkins = new JenkinsServer(new URI(jenkinsURI));
        }

        if(jenkins!=null)  {
            jobs= jenkins.getJobs();
        }

    }


    public Map<String, Job> getAllJobs(){
        return jobs;
    }

    public JobWithDetails getJobDetails(String jobName) throws IOException {
        if(jobName!=null && !jobName.isEmpty())  {
            return jobs.get(jobName).details();
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
