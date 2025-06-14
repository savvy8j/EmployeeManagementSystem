package org.example.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

import java.util.Optional;
import java.util.Set;

public class JwtAuthenticator implements Authenticator<String, User> {

    @Override
    public Optional<User> authenticate(final String token) {

        try {
            String usernameFromJWT = JwtUtil.getUsernameFromJWT(token);
            return Optional.of(new User(usernameFromJWT, Set.of("ADMIN")));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
