package org.example.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class EmployeeNotFoundExceptionMapper implements ExceptionMapper<EmployeeNotFoundException> {

    @Override
    public Response toResponse(final EmployeeNotFoundException e)
    {
        return Response.status(Response.Status.NOT_FOUND).
                entity(new ErrorResponse(e.getMessage()))
                        .build();

    }
}
