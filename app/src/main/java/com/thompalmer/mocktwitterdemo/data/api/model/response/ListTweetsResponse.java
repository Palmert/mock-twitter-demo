package com.thompalmer.mocktwitterdemo.data.api.model.response;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Error;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.ListTweetSuccess;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;

import org.joda.time.DateTime;

import java.util.List;

public class ListTweetsResponse {
    public ListTweetSuccess success;
    public Error error;

    public static ListTweetsResponse success(List<Tweet> tweets, String lastCreatedAt) {
        return new ListTweetsResponse(tweets, lastCreatedAt, null, null) ;
    }

    public static ListTweetsResponse failure(Integer code, String message) {
        return new ListTweetsResponse(null, null, code, message);
    }

    private ListTweetsResponse(List<Tweet> tweets, String lastCreatedAt, Integer code, String message) {
        this.success = tweets == null || lastCreatedAt == null ? null : new ListTweetSuccess(tweets, lastCreatedAt);
        this.error = code == null || message == null ? null : new Error(code, message);

    }
}
