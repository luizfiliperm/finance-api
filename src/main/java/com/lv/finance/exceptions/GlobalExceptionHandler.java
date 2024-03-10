package com.lv.finance.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleException(FinanceException ex){
        return getErrorMessage(ex.getHttpStatus(), ex.getMessage());
    }

    private static ResponseEntity<ErrorMessage> getErrorMessage(HttpStatus httpStatus, String message){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return new ResponseEntity<>(new ErrorMessage(message, request.getContextPath(), System.currentTimeMillis(), httpStatus.value()), httpStatus);
    }
}
