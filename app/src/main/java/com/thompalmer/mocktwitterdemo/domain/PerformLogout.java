package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;
import javax.inject.Named;

import static com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase.*;

public class PerformLogout {
    private final UserSessionInteractor sessionPersister;
    private final RepositoryInteractor<Tweet> tweetRepository;

    @Inject
    public PerformLogout(UserSessionInteractor sessionPersister, RepositoryInteractor<Tweet> tweetRepository) {
        this.sessionPersister = sessionPersister;
        this.tweetRepository = tweetRepository;
    }

    public void execute() {
        sessionPersister.clear();
        tweetRepository.clear();
    }

}
