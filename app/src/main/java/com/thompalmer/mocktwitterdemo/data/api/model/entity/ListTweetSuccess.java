package com.thompalmer.mocktwitterdemo.data.api.model.entity;

import java.util.List;

public class ListTweetSuccess {
    public List<Tweet> tweets;
    public String lastCreatedAt;

    public ListTweetSuccess(List<Tweet> tweets, String lastCreatedAt) {
        this.tweets = tweets;
        this.lastCreatedAt = lastCreatedAt;
    }
}
