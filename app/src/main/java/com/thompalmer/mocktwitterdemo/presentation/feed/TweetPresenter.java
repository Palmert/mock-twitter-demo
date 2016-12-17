package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

import javax.inject.Inject;

public class TweetPresenter {
    private final PerformLogout performLogout;

    @Inject
    public TweetPresenter(PerformLogout performLogout) {
        this.performLogout = performLogout;
    }

    public void onLogoutPressed() {
        performLogout.execute();
    }
}
