package com.office.exceptions;

import java.io.Serializable;

public class ErrorMessage implements Serializable {
    String message;

    public ErrorMessage() {
    }

    public ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
