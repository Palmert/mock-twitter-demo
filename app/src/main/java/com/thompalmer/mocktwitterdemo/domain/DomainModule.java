package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserSessionStorage;

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
    PerformLogout providePerformLogout(UserSessionPersister sessionPersister, TwitterServerDatabase serverDb, TwitterDatabase db) {
        return new PerformLogout(sessionPersister, serverDb, db);
    }

    @Provides
    @ApplicationScope
    ListTweets provideListTweets(TwitterService twitterService, TwitterDatabase twitterDatabase, UserSessionPersister sessionPersister) {
        return new ListTweets(twitterService, twitterDatabase, sessionPersister);
    }

    @Provides
    @ApplicationScope
    CreateTweet providesCreateTweet(TwitterService twitterService, UserSessionPersister sessionPersister) {
        return new CreateTweet(twitterService, sessionPersister);
    }
}
