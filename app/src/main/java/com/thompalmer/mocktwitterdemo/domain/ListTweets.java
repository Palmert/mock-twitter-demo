package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ListTweets {
    private final LocalTwitterServer twitterService;
    private final TwitterDatabase db;
    private final StringPreference userEmailPref;
    private final LongPreference authTokenPref;
//    private final StringPreference lastCreatedAt;

    @Inject
    public ListTweets(LocalTwitterServer twitterService, TwitterDatabase db,
                        @UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        this.twitterService = twitterService;
        this.db = db;
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public Observable<ListTweetsResponse> execute(String count, String lastCreatedAt) {
        return twitterService.listTweets(userEmailPref.get(), authTokenPref.get(), count, lastCreatedAt).doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
        BriteDatabase.Transaction transaction = db.get().newTransaction();
        for(Tweet tweet : listTweetsResponse.success.tweets) {
            db.get().insert(SqlTweet.TABLE, SqlTweet.build(tweet));
        }
        transaction.markSuccessful();
        transaction.end();
    }
}
