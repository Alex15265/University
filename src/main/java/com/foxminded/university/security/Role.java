package com.foxminded.university.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.collectingAndThen;

public enum Role {
    USER(Stream.of(Permission.READ)
            .collect(collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet))),
    ADMIN(Stream.of(Permission.READ, Permission.WRITE)
            .collect(collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet)));

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    private final Set<Permission> permissions;

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }
}