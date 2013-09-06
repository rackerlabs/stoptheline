package com.rackspace.jenkins.stoptheline;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

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

        server.setHandler(context);

        server.start();

        server.join();
    }

}
