package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;

import io.reactivex.Observable;

import static com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer.*;
import static org.mockito.Mockito.*;

public class AttemptUserLoginTest {
    private static final String EMAIL = "email";
    private static final String PASSWORD = "password";
    private static final long AUTH_TOKEN = new Random().nextLong();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    TwitterService twitterService;
    @Mock
    UserSessionPersister userSessionPersister;

    @InjectMocks AttemptUserLogin attemptUserLogin;

    @Test
    public void shouldSaveCredentialsOnSuccessfulLogin() {
        when(twitterService.login(any(LoginRequest.class)))
                .thenReturn(Observable.just(LoginResponse.success(EMAIL, AUTH_TOKEN)));

        attemptUserLogin.execute(EMAIL, PASSWORD).subscribe();

        verify(userSessionPersister).save(EMAIL, AUTH_TOKEN);
    }

    @Test
    public void shouldNotSaveCredentialsOnLoginFailure() {
        Observable<LoginResponse> loginFailureResponse =
                Observable.just(LoginResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_INVALID_PASSWORD));

        when(twitterService.login(any(LoginRequest.class)))
                .thenReturn(loginFailureResponse);

        attemptUserLogin.execute(EMAIL, PASSWORD).subscribe();

        verify(userSessionPersister, never()).save(EMAIL, AUTH_TOKEN);
    }
}
