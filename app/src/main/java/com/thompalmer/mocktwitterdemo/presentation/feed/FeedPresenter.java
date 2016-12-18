package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

import javax.inject.Inject;

import io.reactivex.Observable;

public class FeedPresenter {
    private final PerformLogout performLogout;
    private final ListTweets listTweets;

    @Inject
    public FeedPresenter(PerformLogout performLogout, ListTweets listTweets) {
        this.performLogout = performLogout;
        this.listTweets = listTweets;
    }

    public void onLogoutPressed() {
        performLogout.execute();
    }

    public Observable<ListTweetsResponse> listTweets() {
        return listTweets.execute(String.valueOf(50));
    }
}
