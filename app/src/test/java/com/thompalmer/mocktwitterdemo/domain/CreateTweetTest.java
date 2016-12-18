package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;

import static com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer.*;
import static org.mockito.Mockito.*;

public class CreateTweetTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TwitterService mockTwitterService;

    @Mock
    UserSessionInteractor mockSessionPersister;

    @InjectMocks CreateTweet createTweet;

    @Test
    public void shouldSaveTweetLocallyAfterSuccessfulPost() {
        when(mockTwitterService.postTweet(anyString(), anyLong(), any(PostTweetRequest.class)))
                .thenReturn(Observable.just(TweetResponse.success(any(Tweet.class))));
        createTweet.execute("Test tweet text").subscribe();
        //TODO verify tweet was saved locally
    }


    @Test
    public void shouldNotSaveTweetLocallyAfterFailingToPost() {
        when(mockTwitterService.postTweet(anyString(), anyLong(), any(PostTweetRequest.class)))
                .thenReturn(Observable.just(TweetResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION)));
        createTweet.execute("Test tweet text").subscribe();
        //TODO verify tweet was saved locally
    }
}
