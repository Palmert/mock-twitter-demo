package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;

import static com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase.*;

public class CreateTweet {
    private final TwitterService twitterServer;
    private final UserSessionPersister sessionPersister;
    private final @Named(TWITTER_DB) BriteDatabase db;

    @Inject
    public CreateTweet(TwitterService twitterServer, UserSessionPersister sessionPersister, BriteDatabase db) {
        this.twitterServer = twitterServer;
        this.sessionPersister = sessionPersister;
        this.db = db;
    }

    public Observable<TweetResponse> execute(String text) {
        String email = sessionPersister.getEmail();
        Long authToken = sessionPersister.getAuthToken();
        return twitterServer.postTweet(email, authToken, PostTweetRequest.create(text))
                .doOnNext(tweetResponse -> db.insert(SqlTweet.TABLE, SqlTweet.build(tweetResponse.tweet)));
    }
}
