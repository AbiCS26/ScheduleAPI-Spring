package com.scheduler.scheduleAPI.inputs;

import java.util.UUID;

public class IdGenerator {

    public static String generateID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }
}
