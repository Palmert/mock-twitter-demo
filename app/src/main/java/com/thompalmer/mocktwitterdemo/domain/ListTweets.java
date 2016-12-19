package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ListTweets {
    private final TwitterService twitterService;
    private final RepositoryInteractor<Tweet> tweetRepository;
    private final UserSessionInteractor sessionPersister;

    @Inject
    public ListTweets(TwitterService twitterService, RepositoryInteractor<Tweet> tweetRepository,
                      UserSessionInteractor sessionPersister) {
        this.twitterService = twitterService;
        this.tweetRepository = tweetRepository;
        this.sessionPersister = sessionPersister;
    }

    public Observable<List<Tweet>> execute(String count, String createdAt) {
        if (tweetRepository.shouldUseRemote(createdAt)) {
            return twitterService.listTweets(sessionPersister.getEmail(), sessionPersister.getAuthToken(), count, createdAt)
                    .doOnNext(this::persistTweetsResponse).map(listTweetsResponse ->
                            listTweetsResponse.success != null ? listTweetsResponse.success.tweets : new ArrayList<Tweet>()
                    );
        } else {
            return Observable.just(tweetRepository.paginatedList(createdAt));
        }
    }

    private void persistTweetsResponse(ListTweetsResponse listTweetsResponse) {
        if (listTweetsResponse.success != null) {
            tweetRepository.saveAll(listTweetsResponse.success.tweets, listTweetsResponse.success.lastCreatedAt);
        }
    }
}
