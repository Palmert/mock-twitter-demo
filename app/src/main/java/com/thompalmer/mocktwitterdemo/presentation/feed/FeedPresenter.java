package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.domain.GetLatestTweet;
import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
@FeedScope
public class FeedPresenter {
    private static final int PAGE_SIZE = 50;
    private final PerformLogout performLogout;
    private final ListTweets listTweets;
    private final GetLatestTweet getLatestTweet;
    private boolean requestingTweets;

    @Inject
    public FeedPresenter(PerformLogout performLogout, ListTweets listTweets, GetLatestTweet getLatestTweet) {
        this.performLogout = performLogout;
        this.listTweets = listTweets;
        this.getLatestTweet = getLatestTweet;
    }

    public void onLogoutPressed() {
        performLogout.execute();
    }

    public void listTweets(FeedAdapter adapter) {
        if(!requestingTweets) {
            requestingTweets = true;
            listTweets.execute(String.valueOf(PAGE_SIZE))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(listTweetsResponse -> {
                        adapter.setFeedItems(listTweetsResponse.success.tweets);
                        requestingTweets = false;
                    }, throwable -> requestingTweets = false);
        }
    }

    public Observable<Tweet> displayLatestTweet(String createdAt) {
        return getLatestTweet.execute(createdAt);
    }
}
