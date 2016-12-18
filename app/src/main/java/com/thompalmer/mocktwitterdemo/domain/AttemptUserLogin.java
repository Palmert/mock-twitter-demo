package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AttemptUserLogin {
    private final LocalTwitterServer twitterService;
    private final StringPreference userEmailPref;
    private final LongPreference authTokenPref;

    @Inject
    public AttemptUserLogin(LocalTwitterServer twitterService, @UserEmailPref StringPreference userEmailPref,
                            @AuthTokenPref LongPreference authTokenPref) {
        this.twitterService = twitterService;
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public Observable<LoginResponse> execute(String email, String password) {
        return twitterService.login(LoginRequest.create(email, password)).doOnNext(loginResponse -> {
            if(loginResponse.loginSuccess != null) {
                persistSessionInfo(loginResponse);
            }
        });
    }

    private void persistSessionInfo(LoginResponse loginResponse) {
        userEmailPref.set(loginResponse.loginSuccess.email);
        authTokenPref.set(loginResponse.loginSuccess.authToken);
    }
}
