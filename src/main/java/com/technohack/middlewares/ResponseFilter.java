package com.technohack.middlewares;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;


/*
 *  In this example, we create a LoggingMiddleware class that implements both
 * ContainerRequestFilter and ContainerResponseFilter interfaces.
 * In the filter method of the ContainerRequestFilter interface,
 *  we log the incoming request method and URI. In the filter method of the ContainerResponseFilter interface,
 *  we log the outgoing response status code. We use the org.slf4j.
 * Logger class to write the logs to a file or a console, depending on the logging configuration of the application.
 *  We can add this middleware to our Dropwizard application by calling
 * the environment.jersey().register(new LoggingMiddleware()) method in the run method of the Application class.
 *
 * */
public class ResponseFilter implements  ContainerResponseFilter {

    private final String customHeader;

    public ResponseFilter(String customHeader) {
        this.customHeader = customHeader;
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        responseContext.getHeaders().add("X-Custom-Header", customHeader);
    }

}
