package com.scheduler.scheduleAPI.security;

import com.google.common.collect.Sets;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.scheduler.scheduleAPI.security.UserRolePermission.*;

public enum UserRole {
    GUEST(Sets.newHashSet(EVENT_READ, CONTACT_READ)),
    OWNER(Sets.newHashSet(EVENT_READ, EVENT_WRITE, CONTACT_READ, CONTACT_WRITE, CALENDAR_READ, CALENDAR_WRITE));

    private final Set<UserRolePermission> permissions;

    UserRole(Set<UserRolePermission> permissions) {
        this.permissions = permissions;
    }

    public Set<UserRolePermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return authorities;
    }
}
