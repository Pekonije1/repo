package com.office.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ServiceException extends Exception {
    private static final long serialVersionUID = 1L;
    private String errorMessage;
    private Integer httpStatusCode;

    public ServiceException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatusCode = 400;
    }

    public ServiceException(String errorMessage, int httpStatusCode) {
        super(errorMessage);
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }
}
