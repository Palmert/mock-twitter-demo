package com.thompalmer.mocktwitterdemo.data.api.model.request;

public class LoginRequest {
    private final String email;
    private final String password;

    private LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LoginRequest create(String email, String password) {
        return new LoginRequest(email, password);
    }
}
