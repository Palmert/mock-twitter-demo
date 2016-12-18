package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;

import javax.inject.Inject;
import javax.inject.Named;

public class PerformLogout {
    private final UserSessionPersister sessionPersister;
    private final @Named("TwitterServerDb") BriteDatabase serverDb;
    private final @Named("TwitterDb") BriteDatabase db;

    @Inject
    public PerformLogout(UserSessionPersister sessionPersister, BriteDatabase serverDb, BriteDatabase db) {
        this.sessionPersister = sessionPersister;
        this.serverDb = serverDb;
        this.db = db;
    }

    public void execute() {
        sessionPersister.clear();
        db.delete(SqlTweet.TABLE, null);
        serverDb.delete(SqlSession.TABLE, null);
    }

}
