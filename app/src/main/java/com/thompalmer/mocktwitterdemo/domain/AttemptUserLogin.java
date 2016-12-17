package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AttemptUserLogin {
    private final LocalTwitterServer twitterService;

    @Inject
    public AttemptUserLogin(LocalTwitterServer twitterService) {
        this.twitterService = twitterService;
    }

    public Observable<LoginResponse> execute(String email, String password) {
        return twitterService.login(LoginRequest.create(email, password)).doOnNext(loginResponse -> {
                //TODO Save email and authToken in userPreferences
        });
    }
}
