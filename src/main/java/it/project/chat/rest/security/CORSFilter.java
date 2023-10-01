package it.project.chat.rest.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

@Provider
@PreMatching
public class CORSFilter implements ContainerResponseFilter, ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext,
            ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(
                "Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Credentials", "*");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Headers",
                "*,Origin, X-Requested-With, Content-Type, Accept, Authorization, X-Custom-header,X-Requested-With, content-type, access-control-allow-origin, access-control-allow-methods, access-control-allow-headers");
        responseContext.getHeaders().add(
                "Access-Control-Allow-Methods",
                "GET, POST, OPTIONS, PUT, DELETE, HEAD");
        responseContext.getHeaders().add(
                "Access-Control-Expose-Headers",
                "Location,Authorization, *");

    }

    @Override
    public void filter(ContainerRequestContext request) throws IOException {
        if (request.getHeaderString("Origin") != null && request.getMethod().equalsIgnoreCase("OPTIONS")) {
            request.abortWith(Response.ok().build());
        }
    }
}
