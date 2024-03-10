package com.lv.finance.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FinanceException extends RuntimeException{

    private final HttpStatus httpStatus;

    public FinanceException(String msg, HttpStatus httpStatus){
        super(msg);
        this.httpStatus = httpStatus;
    }

}
