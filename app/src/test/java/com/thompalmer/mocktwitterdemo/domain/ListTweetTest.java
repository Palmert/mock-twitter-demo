package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ListTweetTest {
    private final LocalTwitterServer twitterService;
    private final RepositoryInteractor<Tweet> tweetRepository;
    private final StringPreference userEmailPref;
    private final LongPreference authTokenPref;
//    private final StringPreference lastCreatedAt;

    @Inject
    public ListTweetTest(LocalTwitterServer twitterService, RepositoryInteractor<Tweet> tweetRepository,
                         @UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public Observable<ListTweetsResponse> execute(String count, String lastCreatedAt) {
        return twitterService.listTweets(userEmailPref.get(), authTokenPref.get(), count, lastCreatedAt).doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
//        twee
    }
}
