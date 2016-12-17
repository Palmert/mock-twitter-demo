package com.thompalmer.mocktwitterdemo.data.api.model.response;

public class Success {
    public String email;
    public Long authToken;

    public Success(String email, Long authToken) {
        this.email = email;
        this.authToken = authToken;
    }
}
