package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;

import io.reactivex.Observable;

import static com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer.*;
import static org.mockito.Mockito.*;

public class ListTweetTest {
    private static final String LIMIT = "50";
    private static final String LAST_CREATED_AT = "";
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TwitterService mockTwitterService;

    @Mock
    UserSessionInteractor mockSessionPersister;

    @Mock
    RepositoryInteractor<Tweet> mockTweetRepository;

    @InjectMocks ListTweets listTweets;

    @Before
    public void setup() {
        when(mockSessionPersister.getEmail()).thenReturn("email");
        when(mockSessionPersister.getAuthToken()).thenReturn(1L);
    }

    @Test
    public void shouldStoreFetchedTweetsOnSuccess() {
        when(mockTwitterService.listTweets(anyString(), anyLong(), anyString(), anyString()))
                .thenReturn(Observable.just(ListTweetsResponse.success(new ArrayList<>(), LAST_CREATED_AT)));
        listTweets.execute(LIMIT, LAST_CREATED_AT).subscribe();
        verify(mockTweetRepository).saveAll(anyList());
    }

    @Test
    public void shouldNotAttemptToStoreOnFailure() {
        when(mockTwitterService.listTweets(anyString(), anyLong(), anyString(), anyString()))
                .thenReturn(Observable.just(ListTweetsResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION)));
        listTweets.execute(LIMIT, LAST_CREATED_AT).subscribe();

        verify(mockTweetRepository, never()).saveAll(anyList());
    }
}
