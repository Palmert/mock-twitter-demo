package com.thompalmer.mocktwitterdemo.presentation.tweet;

import com.thompalmer.mocktwitterdemo.domain.CreateTweet;
import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateTweetPresenter {
    private final CreateTweet createTweet;

    @Inject
    public CreateTweetPresenter(CreateTweet createTweet) {
        this.createTweet = createTweet;
    }


    public void onTweetClicked(String tweetContent) {
        createTweet.execute(tweetContent);
    }
}
