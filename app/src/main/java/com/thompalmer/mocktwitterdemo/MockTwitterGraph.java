package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.domain.UserLoginAttempt;

public interface MockTwitterGraph {
    void inject(MockTwitterApp app);

    UserLoginAttempt userLoginAttempt();
}
