package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;

import static com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase.*;

public class ListTweets {
    private final TwitterService twitterService;
    private final @Named(TWITTER_DB) BriteDatabase db;
    private final UserSessionInteractor sessionPersister;

    @Inject
    public ListTweets(TwitterService twitterService, BriteDatabase db, UserSessionInteractor sessionPersister) {
        this.twitterService = twitterService;
        this.db = db;
        this.sessionPersister = sessionPersister;
    }

    public Observable<ListTweetsResponse> execute(String count, String lastCreatedAt) {
        return twitterService.listTweets(sessionPersister.getEmail(),
                    sessionPersister.getAuthToken(), count, lastCreatedAt).doOnNext(this::persistTweetsResponse);
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
