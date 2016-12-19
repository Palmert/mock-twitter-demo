package com.thompalmer.mocktwitterdemo.presentation.tweet;

import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.domain.CreateTweet;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CreateTweetPresenter {
    private final CreateTweet createTweet;

    @Inject
    public CreateTweetPresenter(CreateTweet createTweet) {
        this.createTweet = createTweet;
    }


    public Observable<TweetResponse> onPostTweetClicked(String tweetContent) {
        return createTweet.execute(tweetContent);
    }
}
