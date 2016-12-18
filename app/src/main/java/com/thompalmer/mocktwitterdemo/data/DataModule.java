package com.thompalmer.mocktwitterdemo.data;

import android.app.Application;
import android.content.SharedPreferences;

import com.squareup.sqlbrite.SqlBrite;
import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.repository.TweetRepository;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserSessionStorage;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

import static com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase.*;

@Module
public class DataModule {

    @Provides
    @ApplicationScope
    SqlBrite providesSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @ApplicationScope
    @Named("TwitterServerDb")
    DatabaseInteractorImpl provideTwitterServerDatabase(Application application, SqlBrite sqlBrite) {
        return new TwitterServerDatabase(application, sqlBrite);
    }


    @Provides
    @ApplicationScope
    @Named("TwitterDb")
    DatabaseInteractorImpl provideTwitterDatabase(Application application, SqlBrite sqlBrite) {
        return new TwitterDatabase(application, sqlBrite);
    }

    @Provides
    @ApplicationScope
    RepositoryInteractor<Tweet> provideTweetRepository(@Named(TWITTER_DB) DatabaseInteractorImpl twitterDatabase) {
        return new TweetRepository(twitterDatabase);
    }

    @Provides
    @ApplicationScope
    @UserEmailPref
    StringPreference providesUserEmail(SharedPreferences preferences) {
        return new StringPreference(preferences, "user_email", null);
    }

    @Provides
    @ApplicationScope
    @AuthTokenPref
    LongPreference providesAuthToken(SharedPreferences preferences) {
        return new LongPreference(preferences, "auth_token", -1L);
    }

    @Provides
    @ApplicationScope
    UserSessionStorage provideUserSessionStorage(@UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref) {
        return new UserSessionStorage(userEmailPref, authTokenPref);
    }

}
