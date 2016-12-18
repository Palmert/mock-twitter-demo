package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static org.mockito.Mockito.*;

public class PerformLogoutTest {
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    UserSessionInteractor mockSessionPersister;

    @Mock
    RepositoryInteractor<Tweet> mockTweetRepository;

    @InjectMocks PerformLogout performLogout;

    @Test
    public void shouldClearSessionAndLocalTweetsOnLogout() {
        performLogout.execute();
        verify(mockSessionPersister).clear();
        verify(mockTweetRepository).clear();
    }
}
