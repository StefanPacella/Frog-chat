package it.project.chat.rest.security;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import it.project.chat.rest.CustomException;

@Provider
public class AppExceptionMapper implements ExceptionMapper<CustomException> {
		
    @Override
    public Response toResponse(CustomException exception) {
        return Response.serverError().entity(exception.getMessage()).type(MediaType.APPLICATION_JSON).build();
    }

}
