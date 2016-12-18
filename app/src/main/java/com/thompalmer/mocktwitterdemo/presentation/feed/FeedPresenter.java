package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class FeedPresenter {
    private static final int PAGE_SIZE = 50;
    private final PerformLogout performLogout;
    private final ListTweets listTweets;
    private boolean requestingTweets;

    @Inject
    public FeedPresenter(PerformLogout performLogout, ListTweets listTweets) {
        this.performLogout = performLogout;
        this.listTweets = listTweets;
    }

    public void onLogoutPressed() {
        performLogout.execute();
    }

    public void listTweets(FeedAdapter adapter) {
        if(!requestingTweets) {
            requestingTweets = true;
            listTweets.execute(String.valueOf(PAGE_SIZE), adapter.getLastCreatedAt())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(listTweetsResponse -> {
                        adapter.setFeedItems(listTweetsResponse.tweets);
                        requestingTweets = false;
                    });
        }
    }
}
