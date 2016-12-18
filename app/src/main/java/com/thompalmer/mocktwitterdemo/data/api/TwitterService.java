package com.thompalmer.mocktwitterdemo.data.api;

import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TwitterService {
    @POST("/api/1.1/user/session")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/api/1.1/tweets/{count}/{createdAt}")
    Observable<ListTweetsResponse> listTweets(@Path("count") String count, @Path("createdAt") String createdAt);
}
