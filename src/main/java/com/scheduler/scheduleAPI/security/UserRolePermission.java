package com.scheduler.scheduleAPI.security;

public enum UserRolePermission {
    EVENT_READ("event:read"),
    EVENT_WRITE("event:write"),
    CONTACT_READ("contact:read"),
    CONTACT_WRITE("contact:write"),
    CALENDAR_READ("calendar:read"),
    CALENDAR_WRITE("calendar:write");

    private final String permission;

    UserRolePermission(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }
}
