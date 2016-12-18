package com.thompalmer.mocktwitterdemo.presentation.tweet;

import com.thompalmer.mocktwitterdemo.TwitterComponent;

import dagger.Component;

@CreateTweetScope
@Component(dependencies = TwitterComponent.class)
public interface CreateTweetComponent {
    void inject(CreateTweetActivity activity);
    void inject(CreateTweetViewModel createTweetViewModel);
}
