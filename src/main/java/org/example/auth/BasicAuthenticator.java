package org.example.auth;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

import java.util.Optional;
import java.util.Set;

public class BasicAuthenticator implements Authenticator<BasicCredentials, User> {

    @Override
    public Optional<User> authenticate(BasicCredentials basicCredentials) throws AuthenticationException {
        if ("user".equals(basicCredentials.getUsername()) && "1234".equals(basicCredentials.getPassword())) {
            return Optional.of(new User(basicCredentials.getUsername(), Set.of("USER")));
        }
        if ("admin".equals(basicCredentials.getUsername()) && "1234".equals(basicCredentials.getPassword())) {
            return Optional.of(new User(basicCredentials.getUsername(), Set.of("ADMIN")));
        }
        return Optional.empty();
    }
}
