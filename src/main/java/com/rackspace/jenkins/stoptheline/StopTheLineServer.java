package com.rackspace.jenkins.stoptheline;

import com.rackspace.jenkins.stoptheline.servlet.StopTheLineServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

import java.util.Properties;

/**
 * Embedded Jetty Server
 */
public class StopTheLineServer {

    static final String propertyFilename = "stl.properties";

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties(); //Create properties object.
        properties.load(new FileInputStream("stl.properties")); //Load properties file.

        //Properties to choose from: stlPort, goodBuildURL, badBuildURL, jenkinsURL, buildsToMonitor.

        int port = Integer.valueOf(properties.getProperty("stlPort")); //Get port from properties file.

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);

        context.addServlet(new ServletHolder(genStopTheLineServlet(properties)), "/");

        server.setHandler(context);
        server.start();
        server.join();
    }

    private static List<String> getBuildsToMonitor(String property) {

        List<String> builds = new ArrayList<String>();

        builds = Arrays.asList(property.split(","));

        return builds;
    }

    private static StopTheLineServlet genStopTheLineServlet(Properties properties) throws IOException, URISyntaxException {

        return new StopTheLineServlet(properties.getProperty("jenkinsURL"),
                properties.getProperty("goodBuildURL"),
                properties.getProperty("badBuildURL"),
                getBuildsToMonitor(properties.getProperty("buildsToMonitor")));
    }

}
