package com.thompalmer.mocktwitterdemo.presentation.feed;

import com.thompalmer.mocktwitterdemo.TwitterComponent;
import dagger.Component;

@FeedScope
@Component(dependencies = TwitterComponent.class)
public interface FeedComponent {
    void inject(FeedActivity activity);
    void inject(FeedViewModel feedViewModel);
}
