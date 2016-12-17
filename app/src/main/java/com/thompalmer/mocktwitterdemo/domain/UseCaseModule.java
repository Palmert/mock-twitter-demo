package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {
    @Provides
    @ApplicationScope
    AttemptUserLogin provideAttemptUserLogin(LocalTwitterServer twitterService, @UserEmailPref StringPreference userEmailPref,
                                             @AuthTokenPref LongPreference authTokenPref) {
        return new AttemptUserLogin(twitterService, userEmailPref, authTokenPref);
    }

    @Provides
    @ApplicationScope
    PerformLogout providePerformLogout(@UserEmailPref StringPreference userEmailPref,
                                             @AuthTokenPref LongPreference authTokenPref) {
        return new PerformLogout(userEmailPref, authTokenPref);
    }
}
