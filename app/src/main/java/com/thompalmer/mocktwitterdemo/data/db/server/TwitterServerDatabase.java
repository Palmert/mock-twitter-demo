package com.thompalmer.mocktwitterdemo.data.db.server;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

public class TwitterServerDatabase {
    private final BriteDatabase db;

    @Inject
    public TwitterServerDatabase(TwitterServerDbHelper dbHelper, SqlBrite sqlBrite) {
        db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
    }

    public BriteDatabase getDb() {
        return db;
    }
}
