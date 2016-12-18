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
    private final StringPreference userEmailPref;
    private final LongPreference authTokenPref;
    private final TwitterServerDatabase serverDb;
    private final TwitterDatabase db;

    @Inject
    public PerformLogout(@UserEmailPref StringPreference userEmailPref, @AuthTokenPref LongPreference authTokenPref,
                         TwitterServerDatabase serverDb, TwitterDatabase db) {
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
        this.serverDb = serverDb;
        this.db = db;
    }

    public void execute() {
        clearSessionInfo();
        db.get().delete(SqlTweet.TABLE, null);
        serverDb.get().delete(SqlSession.TABLE, null);
    }

    private void clearSessionInfo() {
        userEmailPref.remove();
        authTokenPref.remove();
    }
}
