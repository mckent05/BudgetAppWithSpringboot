package com.newDemom.BudgetApplication.Exception;

import org.springframework.http.HttpStatus;

public class BlogAPIException extends RuntimeException {

    private String message;
    private HttpStatus httpStatus;

    public BlogAPIException(String message, HttpStatus httpStatus) {
        super(message);
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
