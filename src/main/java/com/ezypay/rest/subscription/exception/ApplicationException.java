package com.ezypay.rest.subscription.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ApplicationException extends Throwable implements ExceptionMapper<Throwable>
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 8584005191016909470L;

	@Override
    public Response toResponse(Throwable exception)
    {
        return Response.status(Status.BAD_REQUEST).entity(exception.getMessage()).build(); 
    }



}
