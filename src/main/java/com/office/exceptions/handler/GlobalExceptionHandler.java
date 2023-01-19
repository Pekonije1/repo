package com.office.exceptions.handler;

import com.office.exceptions.ErrorMessageUtil;
import com.office.exceptions.ServiceException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleAll(Exception ex) {
        if (ex instanceof ServiceException) {
            ServiceException serviceException = (ServiceException) ex;
            return ResponseEntity.status(serviceException.getHttpStatusCode()).body(ErrorMessageUtil.getErrorMessage(serviceException));
        }
        return ResponseEntity.badRequest().body(ErrorMessageUtil.getErrorMessage(ex));
    }
}
