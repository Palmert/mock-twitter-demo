package com.thompalmer.mocktwitterdemo.data.api.model.entity;

public class Error {
    public int code;
    public String message;

    public Error(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
