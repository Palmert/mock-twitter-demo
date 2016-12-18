package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;

public class ListTweets {
    private final TwitterService twitterService;
    private final @Named("TwitterDb") BriteDatabase db;
    private final UserSessionPersister sessionPersister;

    @Inject
    public ListTweets(TwitterService twitterService, BriteDatabase db, UserSessionPersister sessionPersister) {
        this.twitterService = twitterService;
        this.db = db;
        this.sessionPersister = sessionPersister;
    }

    public Observable<ListTweetsResponse> execute(String count, String lastCreatedAt) {
        return twitterService.listTweets(sessionPersister.getEmail(),
                    sessionPersister.getAuthTokenPref(), count, lastCreatedAt).doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
        BriteDatabase.Transaction transaction = db.newTransaction();
        for(Tweet tweet : listTweetsResponse.success.tweets) {
            db.insert(SqlTweet.TABLE, SqlTweet.build(tweet));
        }
        transaction.markSuccessful();
        transaction.end();
    }
}
