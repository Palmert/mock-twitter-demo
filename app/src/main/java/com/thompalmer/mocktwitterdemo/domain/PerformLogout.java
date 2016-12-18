package com.thompalmer.mocktwitterdemo.domain;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Inject;
import javax.inject.Named;

import static com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase.*;
import static com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase.*;

public class PerformLogout {
    private final UserSessionInteractor sessionPersister;
    private final @Named(TWITTER_SERVER_DB) BriteDatabase serverDb;
    private final @Named(TWITTER_DB) BriteDatabase db;

    @Inject
    public PerformLogout(UserSessionInteractor sessionPersister, BriteDatabase serverDb, BriteDatabase db) {
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
