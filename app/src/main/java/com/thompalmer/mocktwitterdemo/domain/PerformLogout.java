package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreferences;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;

import javax.inject.Inject;

public class PerformLogout {
    private final StringPreference userEmailPref;
    private final LongPreferences authTokenPref;

    @Inject
    public PerformLogout( @UserEmailPref StringPreference userEmailPref,
                         @AuthTokenPref LongPreferences authTokenPref) {
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public void execute() {
        clearSessionInfo();
        //TODO Clear database
    }

    private void clearSessionInfo() {
        userEmailPref.remove();
        authTokenPref.remove();
    }
}
