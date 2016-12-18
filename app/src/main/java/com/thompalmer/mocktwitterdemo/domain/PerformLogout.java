package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.db.app.TwitterDatabase;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;

import javax.inject.Inject;

public class PerformLogout {
    private final UserSessionPersister sessionPersister;
    private final TwitterServerDatabase serverDb;
    private final TwitterDatabase db;

    @Inject
    public PerformLogout(UserSessionPersister sessionPersister, TwitterServerDatabase serverDb, TwitterDatabase db) {
        this.sessionPersister = sessionPersister;
        this.serverDb = serverDb;
        this.db = db;
    }

    public void execute() {
        sessionPersister.clear();
        db.get().delete(SqlTweet.TABLE, null);
        serverDb.get().delete(SqlSession.TABLE, null);
    }

}
