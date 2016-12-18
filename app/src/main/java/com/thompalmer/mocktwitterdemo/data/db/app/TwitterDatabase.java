package com.thompalmer.mocktwitterdemo.data.db.app;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.squareup.sqlbrite.SqlBrite;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;

import javax.inject.Inject;

public class TwitterDatabase extends DatabaseInteractorImpl<TwitterDatabase.TwitterDbHelper> {
    public static final String TWITTER_DB = "TwitterDb";

    @Inject
    public TwitterDatabase(Application application, SqlBrite sqlBrite) {
       super(new TwitterDbHelper(application), sqlBrite);
    }

    static class TwitterDbHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "Twitter.db";

        TwitterDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SqlTweet.CREATE_TABLE);
        }
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Handle migrations
        }
        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            onUpgrade(db, oldVersion, newVersion);
        }
    }
}
