package com.rackspace.jenkins.stoptheline.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class StopTheLineServlet extends HttpServlet {

    public StopTheLineServlet(){
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        resp.getOutputStream().write(new String("Test Page!").getBytes());
        resp.setStatus(200);
    }
}
