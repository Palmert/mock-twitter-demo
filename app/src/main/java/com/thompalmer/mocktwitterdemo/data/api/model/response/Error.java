package com.thompalmer.mocktwitterdemo.data.api.model.response;

public class Error {
    public int code;
    public String message;

    public Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
