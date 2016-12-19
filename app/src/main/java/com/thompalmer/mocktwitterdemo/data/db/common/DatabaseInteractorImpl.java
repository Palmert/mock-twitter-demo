package com.thompalmer.mocktwitterdemo.data.db.common;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.QueryObservable;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

public class DatabaseInteractorImpl<T extends SQLiteOpenHelper> {
    private final BriteDatabase db;

    @Inject
    public DatabaseInteractorImpl(T dbHelper, SqlBrite sqlBrite) {
        db = sqlBrite.wrapDatabaseHelper(dbHelper, Schedulers.io());
    }

    public QueryObservable createQuery(@NonNull final String table, @NonNull String sql, @NonNull String... args) {
        return db.createQuery(table, sql, args);
    }

    public Cursor query(String sql, @NonNull String... args) {
        return db.query(sql, args);
    }

    public int delete(String table, @Nullable String whereClause, @Nullable String... args) {
        return db.delete(table, whereClause, args);
    }

    public Long insert(String table, ContentValues values) {
        return db.insert(table, values);
    }

    @WorkerThread
    public long insert(@NonNull String table, @NonNull ContentValues values,
                       @BriteDatabase.ConflictAlgorithm int conflictAlgorithm) {
        return db.insert(table, values, conflictAlgorithm);
    }

    public BriteDatabase.Transaction newTransaction() {
        return db.newTransaction();
    }
}
