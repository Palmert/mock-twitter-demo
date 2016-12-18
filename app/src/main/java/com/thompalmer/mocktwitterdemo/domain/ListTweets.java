package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;

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

    public Observable<ListTweetsResponse> execute(String count) {
        return twitterService.listTweets(count, null).doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
//        lastCreatedAt.set(listTweetsResponse.lastCreatedAt);
       //TODO Save last createdAt and tweets in database
    }
}
