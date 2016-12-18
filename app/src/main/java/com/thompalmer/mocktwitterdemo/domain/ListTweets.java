package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ListTweets {
    private final LocalTwitterServer twitterService;
    private final TwitterDatabase db;
//    private final StringPreference lastCreatedAt;

    @Inject
    public ListTweets(LocalTwitterServer twitterService, TwitterDatabase db) {
        this.twitterService = twitterService;
        this.db = db;
//        this.lastCreatedAt = lastCreatedAtPref;
    }

    public Observable<ListTweetsResponse> execute(String count, String lastCreatedAt) {
        return twitterService.listTweets(count, lastCreatedAt).doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
        BriteDatabase.Transaction transaction = db.get().newTransaction();
        for(Tweet tweet : listTweetsResponse.tweets) {
            db.get().insert(SqlTweet.TABLE, SqlTweet.build(tweet));
        }
        transaction.markSuccessful();
        transaction.end();
    }
}
