package org.example.auth;

import lombok.Getter;

import java.security.Principal;
import java.util.Set;

public class User implements Principal {
    private String name;
    @Getter
    private final Set<String> roles;
    public User(String name, Set<String> roles) {
        this.name = name;
        this.roles = roles;
    }

    @Override
    public String getName() {
        return name;
    }
}
