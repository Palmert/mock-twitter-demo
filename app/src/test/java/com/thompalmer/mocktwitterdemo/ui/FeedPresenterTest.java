package com.thompalmer.mocktwitterdemo.ui;

import com.thompalmer.mocktwitterdemo.domain.ListTweets;
import com.thompalmer.mocktwitterdemo.domain.PerformLogout;
import com.thompalmer.mocktwitterdemo.presentation.feed.FeedPresenter;
import com.thompalmer.mocktwitterdemo.presentation.feed.FeedViewBinding;

import org.junit.Before;
import org.junit.Rule;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

public class FeedPresenterTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    FeedViewBinding viewBinding;

    @Mock
    PerformLogout mockPerformLogout;

    @Mock
    ListTweets mockListTweets;

    private FeedPresenter presenter;

    @Before
    public void setup() {
        presenter = new FeedPresenter(mockPerformLogout, mockListTweets, getLatestTweet);
    }
}
