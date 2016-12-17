package com.thompalmer.mocktwitterdemo.data.api.model.request;

public class LoginRequest {
    private String email;
    private String password;

    private LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static LoginRequest create(String email, String password) {
        return new LoginRequest(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
