package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.SharePreferenceWrapper;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Observable;

public class ListTweets {
    private final TwitterService twitterService;
    private final RepositoryInteractor<Tweet> tweetRepository;
    private final UserSessionInteractor sessionPersister;
    private final @Named("last_created_at") SharePreferenceWrapper<String> lastCreatedAt;

    @Inject
    public ListTweets(TwitterService twitterService, RepositoryInteractor<Tweet> tweetRepository,
                      UserSessionInteractor sessionPersister, SharePreferenceWrapper<String> lastCreatedAt) {
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
        this.sessionPersister = sessionPersister;
        this.lastCreatedAt = lastCreatedAt;
    }

    public Observable<ListTweetsResponse> execute(String count) {
        return twitterService.listTweets(sessionPersister.getEmail(), sessionPersister.getAuthToken(), count, lastCreatedAt.get())
                .doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
        if (listTweetsResponse.success != null) {
            tweetRepository.saveAll(listTweetsResponse.success.tweets, listTweetsResponse.success.lastCreatedAt);
        }
    }
}
