package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;

public interface MockTwitterGraph {
    void inject(MockTwitterApp app);

    AttemptUserLogin userLoginAttempt();
}
