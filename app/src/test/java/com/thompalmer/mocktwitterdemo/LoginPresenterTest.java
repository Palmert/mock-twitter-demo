package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;
import com.thompalmer.mocktwitterdemo.presentation.login.LoginPresenter;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import static junit.framework.Assert.*;

public class LoginPresenterTest {
    private static final String INVALID_PASSWORD_TOO_SHORT = "123";
    private static final String VALID_PASSWORD = "12345678";
    private static final String INVALID_EMAIL_DOMAIN_ONLY = "thom.com";
    private static final String VALID_EMAIL = "thomapalmer@gmail.com";

    @Mock
    AttemptUserLogin attemptUserLogin;
    @Mock
    StringPreference userEmailPref;
    @Mock
    LongPreference authTokenPref;

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    private LoginPresenter presenter;

    @Before
    public void setup() {
        presenter = new LoginPresenter(attemptUserLogin, userEmailPref, authTokenPref);
    }

    @Test
    public void hasValidCredentialsForLoginAttempt_invalidEmail_validPassword() {
        assertEquals(presenter.hasValidCredentialsForLoginAttempt(INVALID_EMAIL_DOMAIN_ONLY, VALID_PASSWORD), false);
    }

    @Test
    public void hasValidCredentialsForLoginAttempt_invalidEmail_invalidPassword() {
        assertEquals(presenter.hasValidCredentialsForLoginAttempt(INVALID_EMAIL_DOMAIN_ONLY, INVALID_PASSWORD_TOO_SHORT), false);
    }

    @Test
    public void hasValidCredentialsForLoginAttempt_validEmail_invalidPassword() {
        assertEquals(presenter.hasValidCredentialsForLoginAttempt(VALID_EMAIL, INVALID_PASSWORD_TOO_SHORT), false);
    }

    @Test
    public void hasValidCredentialsForLoginAttempt_validEmail_validPassword() {
        assertEquals(presenter.hasValidCredentialsForLoginAttempt(VALID_EMAIL, VALID_PASSWORD), true);
    }

    @Test
    public void isPasswordValid_invalidPassword() {
        assertEquals(presenter.isPasswordValid(INVALID_PASSWORD_TOO_SHORT), false);
    }

    @Test
    public void isPasswordValid_validPassword() {
        assertEquals(presenter.isPasswordValid(VALID_PASSWORD), true);
    }

    @Test
    public void updatePasswordMessageId_emptyPassword() {
        assertEquals(presenter.updatePasswordMessageId(""), R.string.error_field_required);
    }

    @Test
    public void updatePasswordMessageId_invalidPassword() {
        assertEquals(presenter.updatePasswordMessageId(INVALID_PASSWORD_TOO_SHORT), R.string.error_invalid_password);
    }

    @Test
    public void isEmailValid_invalidEmail() {
        assertEquals(presenter.isEmailValid(INVALID_EMAIL_DOMAIN_ONLY), false);
    }

    @Test
    public void isEmailValid_validEmail() {
        assertEquals(presenter.isEmailValid(VALID_EMAIL), true);
    }

    @Test
    public void updateEmailEmailMessageId_emptyEmail() {
        assertEquals(presenter.updateEmailMessageId(""), R.string.error_field_required);
    }

    @Test
    public void updateEmailEmailMessageId_invalidEmail() {
        assertEquals(presenter.updateEmailMessageId(INVALID_EMAIL_DOMAIN_ONLY), R.string.error_invalid_email);
    }
}
