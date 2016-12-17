package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.MockTwitterService;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {
    @Provides
    @ApplicationScope
    AttemptUserLogin provideUserLoginAttempt(MockTwitterService twitterService) {
        return new AttemptUserLogin(twitterService);
    }
}
