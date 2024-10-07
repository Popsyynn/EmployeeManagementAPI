package com.example.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class EmployeeException extends Throwable {

    //Handling exception
    @ExceptionHandler(value = EmployeeNotFoundException.class)
    public ResponseEntity<Object> exception(EmployeeNotFoundException exception){
        return new ResponseEntity<>("employee not found" , HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = EmployeeAlreadyExist.class)
    public ResponseEntity<Object> exception(EmployeeAlreadyExist exception){
        return new ResponseEntity<>("email is already registered" , HttpStatus.ALREADY_REPORTED);
    }
}