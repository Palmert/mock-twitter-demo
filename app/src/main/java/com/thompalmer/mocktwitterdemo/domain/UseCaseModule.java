package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
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
    AttemptUserLogin provideAttemptUserLogin(LocalTwitterServer localTwitterServer, @UserEmailPref StringPreference userEmailPref,
                                             @AuthTokenPref LongPreference authTokenPref) {
        return new AttemptUserLogin(localTwitterServer, userEmailPref, authTokenPref);
    }

    @Provides
    @ApplicationScope
    PerformLogout providePerformLogout(@UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref,
                                       TwitterServerDatabase serverDb, TwitterDatabase db) {
        return new PerformLogout(userEmailPref, authTokenPref, serverDb, db);
    }

    @Provides
    @ApplicationScope
    ListTweets provideListTweets(LocalTwitterServer localTwitterServer, TwitterDatabase twitterDatabase,
                                 @UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        return new ListTweets(localTwitterServer, twitterDatabase, userEmailPref, authTokenPref);
    }

    @Provides
    @ApplicationScope
    CreateTweet providesCreateTweet(LocalTwitterServer localTwitterServer,
                                        @UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        return new CreateTweet(localTwitterServer, userEmailPref, authTokenPref);
    }
}
