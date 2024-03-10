package com.lv.finance.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity<ErrorMessage> handleFinanceException(FinanceException ex){
        return getErrorMessage(ex.getHttpStatus(), ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<ValidationErrorMessage> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        List<String> errors = ex.getBindingResult().getFieldErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();
        return getValidationErrorMessage(errors);
    }


    private static ResponseEntity<ErrorMessage> getErrorMessage(HttpStatus httpStatus, String message){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return new ResponseEntity<>(new ErrorMessage(message, request.getRequestURI(), System.currentTimeMillis(), httpStatus.value()), httpStatus);
    }

    private static ResponseEntity<ValidationErrorMessage> getValidationErrorMessage(List<String> errors){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        return new ResponseEntity<>(new ValidationErrorMessage("Validation Error", HttpStatus.BAD_REQUEST.value(), System.currentTimeMillis(), request.getRequestURI(), errors), HttpStatus.BAD_REQUEST);
    }
}
