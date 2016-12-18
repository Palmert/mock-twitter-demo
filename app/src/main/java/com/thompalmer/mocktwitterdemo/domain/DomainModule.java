package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserSessionStorage;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {
    @Provides
    @ApplicationScope
    UserSessionPersister providesUserSessionPersister(UserSessionStorage userSessionStorage) {
        return userSessionStorage;
    }

    @Provides
    @ApplicationScope
    AttemptUserLogin provideAttemptUserLogin(TwitterService twitterService, UserSessionPersister sessionPersister) {
        return new AttemptUserLogin(twitterService, sessionPersister);
    }

    @Provides
    @ApplicationScope
    PerformLogout providePerformLogout(UserSessionPersister sessionPersister, @Named("TwitterServerDb") BriteDatabase serverDb,
                                       @Named("TwitterDb") BriteDatabase db) {
        return new PerformLogout(sessionPersister, serverDb, db);
    }

    @Provides
    @ApplicationScope
    ListTweets provideListTweets(TwitterService twitterService, @Named("TwitterDb") BriteDatabase twitterDatabase, UserSessionPersister sessionPersister) {
        return new ListTweets(twitterService, twitterDatabase, sessionPersister);
    }

    @Provides
    @ApplicationScope
    CreateTweet providesCreateTweet(TwitterService twitterService, UserSessionPersister sessionPersister) {
        return new CreateTweet(twitterService, sessionPersister);
    }
}
