package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.HasCurrentSession;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreferences;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;

import dagger.Module;
import dagger.Provides;

@Module
public class TwitterAppModule {

    private Application application;

    public TwitterAppModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationScope
    Application providesApplication() {
        return application;
    }

    @Provides
    @ApplicationScope
    SharedPreferences providesSharedPreferences(Application application) {
        return PreferenceManager.getDefaultSharedPreferences(application);
    }

    @Provides
    @ApplicationScope
    @UserEmailPref
    StringPreference providesLoginEmail(SharedPreferences preferences) {
        return new StringPreference(preferences, "user_email", null);
    }

    @Provides
    @ApplicationScope
    @AuthTokenPref
    LongPreferences providesAuthToken(SharedPreferences preferences) {
        return new LongPreferences(preferences, "auth_token", -1L);
    }

    @Provides
    @ApplicationScope
    @HasCurrentSession
    Boolean providesHasValidSession(@UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreferences authTokenPref) {
        return userEmailPref.get() != null || authTokenPref.get() != -1L;
    }

}
