package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

/**
 * Created by thompalmer on 2016-12-18.
 */

public class UserSessionStorage implements UserSessionInteractor {
    private final StringPreference userEmailPref;
    private final LongPreference authTokenPref;

    public UserSessionStorage(@UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }


    @Override
    public void save(String email, Long authToken) {
        userEmailPref.set(email);
        authTokenPref.set(authToken);
    }

    @Override
    public String getEmail() {
        return userEmailPref.get();
    }

    public Long getAuthToken() {
        return authTokenPref.get();
    }

    @Override
    public void clear() {
        userEmailPref.clear();
        authTokenPref.clear();
    }

    @Override
    public boolean hasValidSession() {
        return userEmailPref.get() != null && authTokenPref.get() != -1L;
    }
}
