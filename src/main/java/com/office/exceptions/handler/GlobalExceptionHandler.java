package com.office.exceptions.handler;

import com.office.exceptions.ErrorMessageUtil;
import com.office.exceptions.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    /**
     * This method is used to handle exceptions in response. It will make controller methods clear from try and catch blocks.
     * Also, this method will return "unknown error" message if something unexpected happens, but will print stack trace for developer to be able to see and maintain.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        if (ex instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) ex;
            return ResponseEntity.status(serviceException.getHttpStatusCode()).body(ErrorMessageUtil.getErrorMessage(serviceException));
        }
        return ResponseEntity.badRequest().body(ErrorMessageUtil.getErrorMessage(ex));
    }
}
