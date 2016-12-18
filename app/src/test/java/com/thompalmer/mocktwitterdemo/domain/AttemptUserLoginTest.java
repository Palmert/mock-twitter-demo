package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.data.api.LocalTwitterServer;
import com.thompalmer.mocktwitterdemo.data.api.UserSession;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.Random;

import io.reactivex.Observable;

public class AttemptUserLoginTest {
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final long AUTH_TOKEN = new Random().nextLong();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    LocalTwitterServer twitterService;
    @Mock
    UserSession userSession;

    @InjectMocks AttemptUserLogin attemptUserLogin;

    @Test
    public void shouldSaveCredentialsOnSuccessfulLogin() {
        Mockito.when(twitterService.login(LoginRequest.create(EMAIL, PASSWORD)))
                .thenReturn(Observable.just(LoginResponse.success(EMAIL, AUTH_TOKEN)));
        attemptUserLogin.execute(EMAIL, PASSWORD);
    }
}
