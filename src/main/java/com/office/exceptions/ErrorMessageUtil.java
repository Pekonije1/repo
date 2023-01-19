package com.office.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ErrorMessageUtil {
    private static final String UNKNOWN_ERROR_MESSAGE = "Unknown error occurred";

    public static ErrorMessage getErrorMessage(Exception exception) {
        if (!(exception instanceof ServiceException)) {
            log.error(exception.getMessage(), exception);
            return new ErrorMessage(UNKNOWN_ERROR_MESSAGE);
        }
        return new ErrorMessage(exception.getMessage());
    }
}
