package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;
import com.thompalmer.mocktwitterdemo.domain.CreateTweet;
import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;
import com.thompalmer.mocktwitterdemo.domain.UserSessionPersister;

public interface TwitterGraph {
    void inject(TwitterApplication app);

    AttemptUserLogin userLoginAttempt();
    PerformLogout performLogout();
    ListTweets listTweets();
    CreateTweet createTweet();

    UserSessionPersister userSessionPersister();

    @UserEmailPref
    StringPreference userEmailPref();

    @AuthTokenPref
    LongPreference authTokenPref();
}
