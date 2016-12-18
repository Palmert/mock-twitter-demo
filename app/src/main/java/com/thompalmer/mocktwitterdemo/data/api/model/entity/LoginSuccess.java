package com.thompalmer.mocktwitterdemo.data.api.model.entity;

public class LoginSuccess {
    public String email;
    public Long authToken;

    public LoginSuccess(String email, Long authToken) {
        this.email = email;
        this.authToken = authToken;
    }
}
