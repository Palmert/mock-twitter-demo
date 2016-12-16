package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import dagger.Module;
import dagger.Provides;

@Module
public class MockTwitterAppModule {

    private Application application;

    public MockTwitterAppModule(Application application) {
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
}
