package com.thompalmer.mocktwitterdemo.data.api;

import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("/api/1.1/user/")
    Observable<LoginResponse> loginUser(@Body LoginRequest loginRequest);
}
