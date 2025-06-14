package org.example.resources;

import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.example.api.LoginDTO;
import org.example.api.LoginResponse;
import org.example.auth.JwtUtil;

@Path("/api/login")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationResource {

    @POST
    public Response login(LoginDTO loginDto) {
        if("admin".equals(loginDto.getUsername()) && "admin".equals(loginDto.getPassword())) {
            String token = JwtUtil.generateJWTToken(loginDto.getUsername());

            LoginResponse loginResponse = LoginResponse.builder()
                    .token(token)
                    .build();

            return Response.ok(loginResponse)
                    .build();
        }
        return Response.status(Response.Status.UNAUTHORIZED)
                .build();

    }
}
