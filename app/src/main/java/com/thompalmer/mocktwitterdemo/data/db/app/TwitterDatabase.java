package com.thompalmer.mocktwitterdemo.data.db.app;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;
import com.thompalmer.mocktwitterdemo.domain.interactor.DatabaseInteractor;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

/**
 * Created by thompalmer on 2016-12-17.
 */

public class TwitterDatabase implements DatabaseInteractor {

    public static final String TWITTER_DB = "TwitterDb";
    private final BriteDatabase db;

    @Inject
    public TwitterDatabase(TwitterDbHelper dbHelper, SqlBrite sqlBrite) {
        db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
    }

    @Override
    public BriteDatabase get() {
        return db;
    }
}
