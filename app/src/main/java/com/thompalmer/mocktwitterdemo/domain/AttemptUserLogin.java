package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.MockTwitterService;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import retrofit2.Response;

public class AttemptUserLogin {
    private final MockTwitterService twitterService;

    @Inject
    public AttemptUserLogin(MockTwitterService twitterService) {
        this.twitterService = twitterService;
    }

    public Observable<LoginResponse> execute(String email, String password) {
        return twitterService.login(LoginRequest.create(email, password)).doOnNext(loginResponse -> {
                //TODO Save email and authToken in userPreferences
        });
    }
}
