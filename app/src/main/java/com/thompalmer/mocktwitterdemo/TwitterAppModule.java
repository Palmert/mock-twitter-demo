package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
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
        return new StringPreference(preferences, "user_email");
    }

    @Provides
    @ApplicationScope
    @AuthTokenPref
    LongPreference providesAuthToken(SharedPreferences preferences) {
        return new LongPreference(preferences, "auth_token");
    }
}
