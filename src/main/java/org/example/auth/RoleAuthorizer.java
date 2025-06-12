package org.example.auth;

import io.dropwizard.auth.Authorizer;
import jakarta.ws.rs.container.ContainerRequestContext;
import org.checkerframework.checker.nullness.qual.Nullable;

public class RoleAuthorizer implements Authorizer<User> {
    @Override
    public boolean authorize(User user, String methodrole, @Nullable ContainerRequestContext containerRequestContext) {
        return user.getRoles().contains(methodrole);
    }
}
