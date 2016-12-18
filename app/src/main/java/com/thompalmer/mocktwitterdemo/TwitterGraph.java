package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.HasCurrentSession;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LastCreatedAtPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;
import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

public interface TwitterGraph {
    void inject(TwitterApplication app);

    AttemptUserLogin userLoginAttempt();
    PerformLogout performLogout();
    ListTweets listTweets();

    @HasCurrentSession
    Boolean hasCurrentSession();

    @LastCreatedAtPref
    StringPreference lastCreatedAt();

    @UserEmailPref
    StringPreference userEmailPref();
}
