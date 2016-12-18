package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CreateTweet {
    private final LocalTwitterServer twitterServer;
    private final UserSessionPersister sessionPersister;

    @Inject
    public CreateTweet(LocalTwitterServer twitterServer, UserSessionPersister sessionPersister) {
        this.twitterServer = twitterServer;
        this.sessionPersister = sessionPersister;
    }

    public Observable<TweetResponse> execute(String text) {
       return twitterServer.postTweet(sessionPersister.getEmail(), sessionPersister.getAuthTokenPref(), PostTweetRequest.create(text));
    }
}
