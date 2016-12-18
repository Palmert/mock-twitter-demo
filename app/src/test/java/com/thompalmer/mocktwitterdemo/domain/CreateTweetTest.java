package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import org.junit.Before;
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

    @Mock
    RepositoryInteractor<Tweet> mockDb;

    @InjectMocks CreateTweet createTweet;

    @Before
    public void setup() {
        when(mockSessionPersister.getEmail()).thenReturn("email");
        when(mockSessionPersister.getAuthToken()).thenReturn(1L);
    }

    @Test
    public void shouldSaveTweetLocallyAfterSuccessfulPost() {
        when(mockTwitterService.postTweet(anyString(), anyLong(), any(PostTweetRequest.class)))
                .thenReturn(Observable.just(TweetResponse.success(new Tweet())));
        createTweet.execute("Test").subscribe();
        verify(mockDb).save(any(Tweet.class));
    }


    @Test
    public void shouldNotSaveTweetLocallyAfterFailingToPost() {
        when(mockTwitterService.postTweet(anyString(), anyLong(), any(PostTweetRequest.class)))
                .thenReturn(Observable.just(TweetResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION)));
        createTweet.execute("Test tweet text").subscribe();
        verify(mockDb, never()).save(any(Tweet.class));
    }
}
