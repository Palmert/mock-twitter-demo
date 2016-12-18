package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AttemptUserLogin {
    private final TwitterService twitterService;
    private final UserSessionInteractor sessionPersister;

    @Inject
    public AttemptUserLogin(TwitterService twitterService, UserSessionInteractor sessionPersister) {
        this.twitterService = twitterService;
        this.sessionPersister = sessionPersister;
    }

    public Observable<LoginResponse> execute(String email, String password) {
        return twitterService.login(LoginRequest.create(email, password))
                .doOnNext(loginResponse -> {
                    if (loginResponse.success != null) {
                        sessionPersister.save(loginResponse.success.email, loginResponse.success.authToken);
                    }
                });
    }
}
