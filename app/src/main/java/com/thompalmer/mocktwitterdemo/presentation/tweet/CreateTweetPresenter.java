package com.thompalmer.mocktwitterdemo.presentation.tweet;

import com.thompalmer.mocktwitterdemo.domain.CreateTweet;

import javax.inject.Inject;

public class CreateTweetPresenter {
    private final CreateTweet createTweet;

    @Inject
    public CreateTweetPresenter(CreateTweet createTweet) {
        this.createTweet = createTweet;
    }


    public void onPostTweetClicked(String tweetContent) {
        createTweet.execute(tweetContent);
    }
}
