package com.thompalmer.mocktwitterdemo.data.repository;

import android.database.Cursor;
import android.util.Log;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.SharePreferenceWrapper;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

public class TweetRepository implements RepositoryInteractor<Tweet> {
    private static final int DEFAULT_LIMIT = 50;
    private final @Named(TwitterDatabase.TWITTER_DB) DatabaseInteractorImpl db;
    private final @Named(TwitterServerDatabase.TWITTER_SERVER_DB) DatabaseInteractorImpl serverDb;
    private final @Named("last_created_at") SharePreferenceWrapper<String> lastCreatedAt;

    @Inject
    public TweetRepository(DatabaseInteractorImpl db, DatabaseInteractorImpl serverDb, SharePreferenceWrapper<String> lastCreatedAt) {
        this.db = db;
        this.serverDb = serverDb;
        this.lastCreatedAt = lastCreatedAt;
    }

    private List<Tweet> getTweets(String lastCreatedAt) {
        List<Tweet> tweets = new ArrayList<>();
        DatabaseInteractorImpl databaseToQuery = shouldUseRemote(lastCreatedAt) ? serverDb : db;
        Cursor cursor = databaseToQuery.query(SqlTweet.LIST, String.valueOf(DEFAULT_LIMIT));
        try {
            while (cursor.moveToNext()) {
                tweets.add(SqlTweet.map(cursor));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return tweets;
    }

    @Override
    public List<Tweet> paginatedList(String lastCreatedAt) {
        if (lastCreatedAt == null) {
            return getTweets(lastCreatedAt);
        }
        List<Tweet> tweets = new ArrayList<>();
        DatabaseInteractorImpl databaseToQuery = shouldUseRemote(lastCreatedAt) ? serverDb : db;
        Cursor cursor = databaseToQuery.query(SqlTweet.LIST_WITH_CREATED_AT, lastCreatedAt, String.valueOf(DEFAULT_LIMIT));
        try {
            while (cursor.moveToNext()) {
                tweets.add(SqlTweet.map(cursor));
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return tweets;
    }

    @Override
    public boolean shouldUseRemote(String lastCreatedAt) {
        if(this.lastCreatedAt.get() == null ) {
            return true;
        } else if (lastCreatedAt == null) {
            return false;
        }

        DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss Z").toFormatter();
        DateTime localLastCreatedAt = DateTime.parse(this.lastCreatedAt.get(), df);
        DateTime requestLastCreatedAt = DateTime.parse(lastCreatedAt, df);
        return localLastCreatedAt.isEqual(requestLastCreatedAt) || localLastCreatedAt.isAfter(requestLastCreatedAt);
    }

    @Override
    public Tweet get(String createdAt) {
        Tweet tweet = null;
        Cursor cursor = db.query(SqlTweet.QUERY, createdAt);
        if (cursor.moveToFirst()) {
            tweet = SqlTweet.map(cursor);
        }
        return tweet;
    }

    @Override
    public void remove(String primaryKey) {
        //No op
    }

    @Override
    public void clear() {
        db.delete(SqlTweet.TABLE, null);
    }

    @Override
    public void save(Tweet item) {
        db.insert(SqlTweet.TABLE, SqlTweet.build(item));
    }

    @Override
    public void saveAll(List<Tweet> items, String lastCreatedAt) {
        DateTimeFormatter df = new DateTimeFormatterBuilder().appendPattern("yyyy-MM-dd'T'HH:mm:ss Z").toFormatter();
        try {
            boolean shouldSaveTweets = this.lastCreatedAt.get() == null;
            if (this.lastCreatedAt.get() != null) {
                DateTime localLastCreatedAt = DateTime.parse(this.lastCreatedAt.get(), df);
                DateTime requestLastCreatedAt = DateTime.parse(lastCreatedAt, df);
                shouldSaveTweets = localLastCreatedAt.isAfter(requestLastCreatedAt);
            }
            if (shouldSaveTweets) {
                this.lastCreatedAt.set(lastCreatedAt);
                BriteDatabase.Transaction transaction = db.newTransaction();
                try {
                    for (Tweet tweet : items) {
                        save(tweet);
                    }
                    transaction.markSuccessful();
                } finally {
                    transaction.end();
                }
            }
        } catch (Exception ex) {
            Log.d("Save all", ex.getMessage());
        }
    }
}
