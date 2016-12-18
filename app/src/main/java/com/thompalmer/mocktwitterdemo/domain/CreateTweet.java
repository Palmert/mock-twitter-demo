package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CreateTweet {
    private final LocalTwitterServer twitterServer;
    private final StringPreference userEmailPref;
    private final LongPreference authTokenPref;

    @Inject
    public CreateTweet(LocalTwitterServer twitterServer, @UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        this.twitterServer = twitterServer;
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public Observable<TweetResponse> execute(String text) {
       return twitterServer.postTweet(userEmailPref.get(), authTokenPref.get(), PostTweetRequest.create(text));
    }
}
