package eu.learnpad.ca.rest.api.web.security;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;




@Provider
public class ResponseCorsFilter implements ContainerResponseFilter {

	
	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Methods:","POST, GET, OPTIONS");
		responseContext.getHeaders().add("Access-Control-Allow-Headers ", "Origin, X-Requested-With, Content-Type, Accept");
		/* responseContext.getHeaders().add("Access-Control-Allow-Headers",
	                "origin, content-type, accept, authorization");
		 responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		 responseContext.getHeaders().add("Access-Control-Allow-Methods",
	                "GET, POST, PUT, DELETE, OPTIONS, HEAD");*/
	}



}
