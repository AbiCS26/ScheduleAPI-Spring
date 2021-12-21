package com.scheduler.scheduleAPI.exception;

public class PermissionException extends RuntimeException {
    public PermissionException() {
        super();
    }

    public PermissionException(String s) {
        super(s);
    }
}
