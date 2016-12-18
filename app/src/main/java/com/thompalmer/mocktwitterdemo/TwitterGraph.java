package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.HasCurrentSession;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

public interface TwitterGraph {
    void inject(TwitterApplication app);

    AttemptUserLogin userLoginAttempt();
    PerformLogout performLogout();

    @HasCurrentSession
    Boolean hasCurrentSession();
}
