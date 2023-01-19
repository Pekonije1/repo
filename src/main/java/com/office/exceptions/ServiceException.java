package com.office.exceptions;

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

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }
}
