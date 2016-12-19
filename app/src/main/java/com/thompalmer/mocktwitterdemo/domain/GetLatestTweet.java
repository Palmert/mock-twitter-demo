package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class GetLatestTweet {
    private final RepositoryInteractor<Tweet> tweetRepository;

    @Inject
    public GetLatestTweet(RepositoryInteractor<Tweet> tweetRepository) {
        this.tweetRepository = tweetRepository;
    }

    public Observable<Tweet> execute(String createdAt) {
        return Observable.just(tweetRepository.get(createdAt));
    }
}
