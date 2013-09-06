package com.rackspace.jenkins.stoptheline;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * Embedded Jetty Server
 */
public class StopTheLineServer {

    public static void main(String[] args) throws Exception {

        Properties properties = new Properties(); //Create properties object.
        properties.load(new FileInputStream("stl.properties")); //Load properties file.

        //Properties to choose from: stlPort, goodBuildURL, badBuildURL, jenkinsURL, buildsToMonitor.

        int port = Integer.valueOf(properties.getProperty("stlPort")); //Get port from properties file.

        Server server = new Server(port);

        WebAppContext context = new WebAppContext();

        context.setContextPath("/");

        server.setHandler(context);

        server.start();

        server.join();
    }
}
