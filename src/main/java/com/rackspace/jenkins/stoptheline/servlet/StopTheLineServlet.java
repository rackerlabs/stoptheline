package com.rackspace.jenkins.stoptheline.servlet;

import com.offbytwo.jenkins.model.Job;
import com.rackspace.jenkins.stoptheline.client.JenkinsClient;
import com.rackspace.jenkins.stoptheline.client.JenkinsClientImpl;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;

public class StopTheLineServlet extends HttpServlet {

    private String jenkinsUrl, goodUrl, badUrl;
    private List<String> builds;
    private JenkinsClient client;

    public StopTheLineServlet(String jenkinsUrl,String goodUrl,String badUrl, List<String> builds) throws IOException, URISyntaxException {

        this.jenkinsUrl = jenkinsUrl;
        this.goodUrl = goodUrl;
        this.badUrl = badUrl;
        this.builds = builds;
        this.client = new JenkinsClientImpl(jenkinsUrl);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.setStatus(200);
        if(isBroken()){
            resp.getOutputStream().write(new String("Bad!" + goodUrl).getBytes());
            resp.sendRedirect(badUrl);
        } else{
            resp.getOutputStream().write(new String("Good!" + goodUrl).getBytes());
        }

    }

    private boolean isBroken() throws IOException {

        Map<String, Job> jobs = client.getAllJobs();

        for(String st: builds){
            if(!jobs.containsKey(st)){
                jobs.remove(st);
            }
        }

        return client.isAllJobsSuccessful(jobs);
    }

}
