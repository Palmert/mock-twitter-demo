package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.domain.GetLatestTweet;
import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

@FeedScope
public class FeedPresenter {
    private static final int PAGE_SIZE = 50;
    private final PerformLogout performLogout;
    private final ListTweets listTweets;
    private final GetLatestTweet getLatestTweet;

    @Inject
    public FeedPresenter(PerformLogout performLogout, ListTweets listTweets, GetLatestTweet getLatestTweet) {
        this.performLogout = performLogout;
        this.listTweets = listTweets;
        this.getLatestTweet = getLatestTweet;
    }

    public void onLogoutPressed() {
        performLogout.execute();
    }

    public Observable<List<Tweet>>listTweets(String createdAt) {
       return listTweets.execute(String.valueOf(PAGE_SIZE), createdAt);
    }

    public Observable<Tweet> displayLatestTweet(String createdAt) {
        return getLatestTweet.execute(createdAt);
    }
}
