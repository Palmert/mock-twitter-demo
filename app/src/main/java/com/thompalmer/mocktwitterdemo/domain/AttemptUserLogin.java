package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreferences;
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
    private final LongPreferences authTokenPref;

    @Inject
    public AttemptUserLogin(LocalTwitterServer twitterService, @UserEmailPref StringPreference userEmailPref,
                            @AuthTokenPref LongPreferences authTokenPref) {
        this.twitterService = twitterService;
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public Observable<LoginResponse> execute(String email, String password) {
        return twitterService.login(LoginRequest.create(email, password)).doOnNext(loginResponse -> {
            if(loginResponse.success != null) {
                persistSessionInfo(loginResponse);
            }
        });
    }

    private void persistSessionInfo(LoginResponse loginResponse) {
        userEmailPref.set(loginResponse.success.email);
        authTokenPref.set(loginResponse.success.authToken);
    }
}
