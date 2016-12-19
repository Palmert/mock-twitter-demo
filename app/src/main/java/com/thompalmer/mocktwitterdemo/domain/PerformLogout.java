package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.SharePreferenceWrapper;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;
import javax.inject.Named;

public class PerformLogout {
    private final UserSessionInteractor sessionPersister;
    private final RepositoryInteractor<Tweet> tweetRepository;
    private final @Named("last_created_at") SharePreferenceWrapper<String> lastCreatedAt;

    @Inject
    public PerformLogout(UserSessionInteractor sessionPersister, RepositoryInteractor<Tweet> tweetRepository, SharePreferenceWrapper<String> lastCreatedAt) {
        this.sessionPersister = sessionPersister;
        this.tweetRepository = tweetRepository;
        this.lastCreatedAt = lastCreatedAt;
    }

    public void execute() {
        sessionPersister.clear();
        tweetRepository.clear();
        lastCreatedAt.clear();
    }

}
