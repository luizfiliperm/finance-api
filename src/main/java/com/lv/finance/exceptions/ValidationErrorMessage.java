package com.lv.finance.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationErrorMessage {

    private String message;
    private int status;
    private Long timestamp;
    private String path;
    private List<String> errors;
}
