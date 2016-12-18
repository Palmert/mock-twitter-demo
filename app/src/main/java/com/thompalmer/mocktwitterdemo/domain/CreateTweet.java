package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CreateTweet {
    private final TwitterService twitterServer;
    private final UserSessionInteractor sessionPersister;
    private final RepositoryInteractor<Tweet> tweetRepository;

    @Inject
    public CreateTweet(TwitterService twitterServer, UserSessionInteractor sessionPersister, RepositoryInteractor<Tweet> tweetRepository) {
        this.twitterServer = twitterServer;
        this.sessionPersister = sessionPersister;
        this.tweetRepository = tweetRepository;
    }

    public Observable<TweetResponse> execute(String text) {
        String email = sessionPersister.getEmail();
        Long authToken = sessionPersister.getAuthToken();
        return twitterServer.postTweet(email, authToken, PostTweetRequest.create(text))
                .doOnNext(tweetResponse -> tweetRepository.save(tweetResponse.tweet));
    }
}
