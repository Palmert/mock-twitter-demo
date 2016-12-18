package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AttemptUserLogin {
    private final LocalTwitterServer twitterService;
    private final UserSessionPersister sessionPersister;

    @Inject
    public AttemptUserLogin(LocalTwitterServer twitterService, UserSessionPersister sessionPersister) {
        this.twitterService = twitterService;
        this.sessionPersister = sessionPersister;
    }

    public Observable<LoginResponse> execute(String email, String password) {
        return twitterService.login(LoginRequest.create(email, password)).doOnNext(loginResponse -> {
            if(loginResponse.success != null) {
                sessionPersister.save(loginResponse.success.email, loginResponse.success.authToken);
            }
        });
    }
}
