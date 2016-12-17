package com.thompalmer.mocktwitterdemo.data.api;

/**
 * Created by thompalmer on 2016-12-16.
 */
public class UserSession {
    public String email;
    public Long authToken;

    public UserSession(String email, long authToken) {
        this.email = email;
        this.authToken = authToken;
    }
}
