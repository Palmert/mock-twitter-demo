package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;

public interface TwitterGraph {
    void inject(TwitterApp app);

    AttemptUserLogin userLoginAttempt();
}
