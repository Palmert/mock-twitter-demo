package com.thompalmer.mocktwitterdemo.data.api.model.response;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Error;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Success;

public class LoginResponse {
    public Success success;
    public Error error;

    public static LoginResponse success(String email, Long authToken) {
        return new LoginResponse(email, authToken, null, null);
    }

    public static LoginResponse failure(int code, String message) {
        return new LoginResponse(null, null, code, message);
    }

    public LoginResponse(String email, Long authToken, Integer code, String message) {
        this.success = email == null || authToken == null ? null : new Success(email, authToken);
        this.error = code == null || message == null ? null : new Error(code, message);
    }
}
