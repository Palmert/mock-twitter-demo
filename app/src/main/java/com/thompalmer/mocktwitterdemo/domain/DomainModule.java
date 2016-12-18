package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserSessionStorage;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase.*;
import static com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase.*;

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
    PerformLogout providePerformLogout(UserSessionPersister sessionPersister, @Named(TWITTER_SERVER_DB) BriteDatabase serverDb,
                                       @Named(TWITTER_DB) BriteDatabase db) {
        return new PerformLogout(sessionPersister, serverDb, db);
    }

    @Provides
    @ApplicationScope
    ListTweets provideListTweets(TwitterService twitterService, @Named(TWITTER_DB) BriteDatabase twitterDatabase, UserSessionPersister sessionPersister) {
        return new ListTweets(twitterService, twitterDatabase, sessionPersister);
    }

    @Provides
    @ApplicationScope
    CreateTweet providesCreateTweet(TwitterService twitterService, UserSessionPersister sessionPersister, @Named(TWITTER_DB) BriteDatabase db) {
        return new CreateTweet(twitterService, sessionPersister, db);
    }
}
