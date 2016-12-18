package com.thompalmer.mocktwitterdemo.data;

import android.app.Application;
import android.content.SharedPreferences;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDbHelper;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDbHelper;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserSessionStorage;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {

    @Provides
    @ApplicationScope
    SqlBrite providesSqlBrite() {
        return new SqlBrite.Builder().build();
    }

    @Provides
    @ApplicationScope
    TwitterServerDbHelper provideTwitterServerDbHelper(Application application) {
        return new TwitterServerDbHelper(application.getApplicationContext());
    }

    @Provides
    @ApplicationScope
    @Named("TwitterServerDb")
    BriteDatabase provideTwitterServerDatabase(TwitterServerDbHelper dbHelper, SqlBrite sqlBrite) {
        return new TwitterServerDatabase(dbHelper, sqlBrite).get();
    }

    @Provides
    @ApplicationScope
    TwitterDbHelper provideTwitterDbHelper(Application application) {
        return new TwitterDbHelper(application.getApplicationContext());
    }

    @Provides
    @ApplicationScope
    @Named("TwitterDb")
    BriteDatabase provideTwitterDatabase(TwitterDbHelper dbHelper, SqlBrite sqlBrite) {
        return new TwitterDatabase(dbHelper, sqlBrite).get();
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
