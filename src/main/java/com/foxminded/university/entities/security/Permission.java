package com.foxminded.university.entities.security;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {
    READ("read"),
    WRITE("write");

    private final String permission;

    public String getPermission() {
        return permission;
    }
}
