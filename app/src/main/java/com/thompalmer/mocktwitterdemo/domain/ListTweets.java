package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;
import io.reactivex.Observable;

public class ListTweets {
    private final TwitterService twitterService;
    private final RepositoryInteractor<Tweet> tweetRepository;
    private final UserSessionInteractor sessionPersister;

    @Inject
    public ListTweets(TwitterService twitterService, RepositoryInteractor<Tweet> tweetRepository, UserSessionInteractor sessionPersister) {
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
        this.sessionPersister = sessionPersister;
    }

    public Observable<ListTweetsResponse> execute(String count, String lastCreatedAt) {
        return twitterService.listTweets(sessionPersister.getEmail(),
                    sessionPersister.getAuthToken(), count, lastCreatedAt).doOnNext(this::persistTweetsResponse);
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
       tweetRepository.saveAll(listTweetsResponse.success.tweets);
    }
}
