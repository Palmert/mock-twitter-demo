package com.thompalmer.mocktwitterdemo.data.api;

import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TwitterService {
    @POST("/api/1.1/user/session")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/1.1/tweet")
    Observable<TweetResponse> postTweet(@Header("user_name") String userName, @Header("auth_token") Long authToken,
                                        @Body PostTweetRequest postTweetRequest);

    @GET("/api/1.1/tweet/list/{count}/{createdAt}")
    Observable<ListTweetsResponse> listTweets(@Header("user_name") String userName, @Header("auth_token") Long authToken,
                                              @Path("count") String count, @Path("createdAt") String createdAt);
}
