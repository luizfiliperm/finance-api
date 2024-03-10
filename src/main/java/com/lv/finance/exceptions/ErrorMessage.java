package com.lv.finance.exceptions;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ErrorMessage {

    private String message;
    private String path;
    private Long timestamp;
    private int status;

}
