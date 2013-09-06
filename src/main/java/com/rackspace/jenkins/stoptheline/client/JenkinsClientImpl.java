package com.rackspace.jenkins.stoptheline.client;

import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.JenkinsServer;
import org.apache.http.client.HttpResponseException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created with IntelliJ IDEA.
 */
public class JenkinsClientImpl implements JenkinsClient{

    public static String  jenkinsURI;
    private JenkinsServer jenkins;
    private JenkinsHttpClient client ;

    public JenkinsClientImpl(String jenkinsURI) throws URISyntaxException,IOException{

        this.jenkinsURI=jenkinsURI;
        jenkins=null;

        if(jenkinsURI!=null && !jenkinsURI.isEmpty()){
            client = new JenkinsHttpClient(new URI(jenkinsURI));
            jenkins = new JenkinsServer(client);
        }

    }


    public Map<String, Job> getAllJobs(){

        Map<String, Job> jobs = new HashMap<String, Job>();
        try {
            jobs =  jenkins.getJobs();
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

    public String getCulpritName(String path)   {

       try{

        if(path!=null && !path.isEmpty())  {
            String jsonText = client.get(path+"/api/json?tree=culprits[fullName]");
            JSONObject json = new JSONObject(jsonText);
            JSONArray culprits = json.getJSONArray("culprits");
            if (culprits!=null && culprits.length()>0)  {
                return culprits.getJSONObject(0).getString("fullName") ;
            }

        }

       }catch(JSONException e){
           e.printStackTrace();
       } catch(HttpResponseException e){
           e.printStackTrace();
       } catch(IOException e) {
           e.printStackTrace();

       }
        return null;
    }


}
