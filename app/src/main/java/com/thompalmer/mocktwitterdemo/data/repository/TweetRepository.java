package com.thompalmer.mocktwitterdemo.data.repository;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kotlin.NotImplementedError;

public class TweetRepository implements RepositoryInteractor<Tweet> {
    private static final int DEFAULT_LIMIT = 50;
    private final DatabaseInteractorImpl db;

    @Inject
    public TweetRepository(DatabaseInteractorImpl db) {
        this.db = db;
    }

    @Override
    public List<Tweet> list() {
        List<Tweet> tweets = new ArrayList<>();
        Cursor cursor = db.query(SqlTweet.LIST, String.valueOf(DEFAULT_LIMIT));
        try {
            while (cursor.moveToNext()) {
                SqlTweet.map(cursor);
            }
        } finally {
            if(cursor != null) {
               cursor.close();
            }
        }
        return tweets;
    }

    @Override
    public Observable<List<Tweet>> listObservable() {
         throw new NotImplementedError("List observable tweets not implemented");
    }

    @Override
    public List<Tweet> paginatedList(String lastCreatedAt) {
        List<Tweet> tweets = new ArrayList<>();
        Cursor cursor = db.query(SqlTweet.LIST_WITH_CREATED_AT, lastCreatedAt, String.valueOf(DEFAULT_LIMIT));
        try {
            while (cursor.moveToNext()) {
                SqlTweet.map(cursor);
            }
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
        return tweets;
    }

    @Override
    public Observable<List<Tweet>> paginatedListObservable(String lastCreatedAt) {
        throw new NotImplementedError("List observable tweets not implemented");
//        return db.createQuery(SqlTweet.TABLE, SqlTweet.LIST_WITH_CREATED_AT, lastCreatedAt, String.valueOf(DEFAULT_LIMIT))
//                 .mapToList(SqlTweet.MAPPER);
    }

    @Override
    public Tweet get(String primaryKey) {
        //No op
        return null;
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
    public void saveAll(List<Tweet> items) {
        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            for (Tweet tweet : items) {
                save(tweet);
            }
            transaction.markSuccessful();
        }finally {
            transaction.end();
        }

    }
}
