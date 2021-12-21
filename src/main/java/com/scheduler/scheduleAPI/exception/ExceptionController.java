package com.scheduler.scheduleAPI.exception;

import com.scheduler.scheduleAPI.response.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.InputMismatchException;

@ControllerAdvice
public class ExceptionController {

    @Autowired
    private ResponseHandler responseHandler;

    @ExceptionHandler(value = InputMismatchException.class)
    public ResponseEntity exception(InputMismatchException exception) {
        return responseHandler.generateResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = PermissionException.class)
    public ResponseEntity exception(PermissionException exception) {
        return responseHandler.generateResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}

