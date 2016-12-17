package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;

import dagger.Module;
import dagger.Provides;

@Module
public class UseCaseModule {
    @Provides
    @ApplicationScope
    AttemptUserLogin provideUserLoginAttempt(LocalTwitterServer twitterService) {
        return new AttemptUserLogin(twitterService);
    }
}
