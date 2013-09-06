package com.rackspace.jenkins.stoptheline;

import com.rackspace.jenkins.stoptheline.servlet.StopTheLineServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.Servlet;
import java.io.FileInputStream;
import java.util.Properties;

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
        context.addServlet(new ServletHolder(new StopTheLineServlet()), "/*");

        server.setHandler(context);
        server.start();
        server.join();
    }

}
