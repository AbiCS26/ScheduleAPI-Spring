package com.scheduler.scheduleAPI.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ResponseHandler {
    public ResponseEntity generateResponse(String message, HttpStatus status, Object data) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        map.put("data", data);

        return new ResponseEntity(map, status);
    }

    public ResponseEntity generateResponse(String message, HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());

        return new ResponseEntity(map, status);
    }
}
