package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
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
    AttemptUserLogin provideAttemptUserLogin(LocalTwitterServer localTwitterServer, UserSessionPersister sessionPersister) {
        return new AttemptUserLogin(localTwitterServer, sessionPersister);
    }

    @Provides
    @ApplicationScope
    PerformLogout providePerformLogout(UserSessionPersister sessionPersister, TwitterServerDatabase serverDb, TwitterDatabase db) {
        return new PerformLogout(sessionPersister, serverDb, db);
    }

    @Provides
    @ApplicationScope
    ListTweets provideListTweets(LocalTwitterServer localTwitterServer, TwitterDatabase twitterDatabase, UserSessionPersister sessionPersister) {
        return new ListTweets(localTwitterServer, twitterDatabase, sessionPersister);
    }

    @Provides
    @ApplicationScope
    CreateTweet providesCreateTweet(LocalTwitterServer localTwitterServer, UserSessionPersister sessionPersister) {
        return new CreateTweet(localTwitterServer, sessionPersister);
    }
}
