package com.thompalmer.mocktwitterdemo.data.api.model.response;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Error;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;

public class TweetResponse {
    public Tweet tweet;
    public Error error;

    public static TweetResponse success(Tweet tweet) {
        return new TweetResponse(tweet, null, null);
    }

    public static TweetResponse failure(int code, String message) {
        return new TweetResponse(null, code, message);
    }

    public TweetResponse(Tweet tweet, Integer code, String message) {
        this.tweet = tweet;
        this.error = code == null || message == null ? null : new Error(code, message);
    }
}
