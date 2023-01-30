package com.example.blog.exception;


public class BusinessException extends RuntimeException {
    private String errInfo;

    public BusinessException(String message){
        super(message);
        errInfo = message;
    }

    public String getErrInfo() {
        return errInfo;
    }
}
