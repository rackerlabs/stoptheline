package com.rackspace.jenkins.stoptheline.servlet;

import com.offbytwo.jenkins.model.Job;
import com.rackspace.jenkins.stoptheline.client.JenkinsClient;
import com.rackspace.jenkins.stoptheline.client.JenkinsClientImpl;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.HashMap;
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

        ServletOutputStream stream = resp.getOutputStream();
        InputStream str;

        if(isBroken()){
            str =StopTheLineServlet.class.getResourceAsStream("bad.html");

        } else{
            str = StopTheLineServlet.class.getResourceAsStream("good.html");
        }

        resp.setHeader("Content-length", Integer.toString(str.available()));
        byte[] payload = new byte[str.available()];
        str.read(payload);

        stream.write(payload);


    }

    private boolean isBroken() throws IOException {

        Map<String, Job> jobs = client.getAllJobs();
        Map<String, Job> checkedJobs = new HashMap<String, Job>();

        for(String st: builds){
            if(jobs.containsKey(st)){
                checkedJobs.put(st,jobs.get(st));
            }
        }

        return client.isAllJobsSuccessful(checkedJobs);
    }

}
