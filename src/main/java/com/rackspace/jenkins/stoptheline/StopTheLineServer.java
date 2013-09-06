package com.rackspace.jenkins.stoptheline;

import com.rackspace.jenkins.stoptheline.servlet.StopTheLineServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;

import javax.servlet.Servlet;

/**
 * Embedded Jetty Server
 */
public class StopTheLineServer {

    public static void main(String[] args) throws Exception {

        // TODO: make port configurable
        int port = 8080;

        Server server = new Server(port);

        WebAppContext context = new WebAppContext();

        context.setContextPath("/");

        context.addServlet(new ServletHolder(new StopTheLineServlet()), "/");
        server.setHandler(context);

        server.start();

        server.join();
    }

}
