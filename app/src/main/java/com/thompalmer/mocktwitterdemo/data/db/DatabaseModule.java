package com.thompalmer.mocktwitterdemo.data.db;

import android.app.Application;

import com.squareup.sqlbrite.SqlBrite;
import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDbHelper;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDbHelper;

import dagger.Module;
import dagger.Provides;

@Module
public class DatabaseModule {

    @Provides
    @ApplicationScope
    TwitterServerDbHelper provideTwitterServerDbHelper(Application application) {
        return new TwitterServerDbHelper(application.getApplicationContext());
    }

    @Provides
    @ApplicationScope
    TwitterServerDatabase provideTwitterServerDatabase(TwitterServerDbHelper dbHelper) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        return new TwitterServerDatabase(dbHelper, sqlBrite);
    }

    @Provides
    @ApplicationScope
    TwitterDbHelper provideTwitterDbHelper(Application application) {
        return new TwitterDbHelper(application.getApplicationContext());
    }

    @Provides
    @ApplicationScope
    TwitterDatabase provideTwitterDatabase(TwitterDbHelper dbHelper) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        return new TwitterDatabase(dbHelper, sqlBrite);
    }
}
