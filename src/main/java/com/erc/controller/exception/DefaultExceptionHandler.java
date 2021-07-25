package com.erc.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class DefaultExceptionHandler {

    public ResponseEntity<ErrorMessage> handleOthersException(Exception e) {
        /* Handles all other exceptions. Status code 500. */
        System.out.println(e.getMessage());
        return new ResponseEntity<>(new ErrorMessage(13L, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
