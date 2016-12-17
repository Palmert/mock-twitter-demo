package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.TwitterComponent;
import dagger.Component;

@TweetScope
@Component(dependencies = TwitterComponent.class)
public interface TweetComponent {
    void inject(TweetActivity activity);
}
