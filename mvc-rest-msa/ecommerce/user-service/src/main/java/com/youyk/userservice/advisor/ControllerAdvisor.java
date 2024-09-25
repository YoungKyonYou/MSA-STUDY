package com.youyk.userservice.advisor;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {

    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e){
        e.printStackTrace();
        return e.getMessage();
    }
}
