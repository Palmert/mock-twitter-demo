package com.thompalmer.mocktwitterdemo.presentation.login;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.AuthTokenPref;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.LongPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoginPresenter {
    private final AttemptUserLogin attemptUserLogin;
    private final @UserEmailPref StringPreference userEmailPref;
    private final @AuthTokenPref LongPreference authTokenPref;

    @Inject
    public LoginPresenter(AttemptUserLogin attemptUserLogin, StringPreference userEmailPref, LongPreference authTokenPref) {
        this.attemptUserLogin = attemptUserLogin;
        this.userEmailPref = userEmailPref;
        this.authTokenPref = authTokenPref;
    }

    public int updateEmailMessageId(String email) {
        int emailMessageId = 0;

        if (email.isEmpty()) {
            emailMessageId = R.string.error_field_required;
        } else if (!isEmailValid(email)) {
            emailMessageId = R.string.error_invalid_email;
        }

        return emailMessageId;
    }

    public boolean isEmailValid(String email) {
        return !email.isEmpty() && email.contains("@");
    }

    public int updatePasswordMessageId(String password) {
        int passwordMessageId = 0;

        if (password.isEmpty()) {
            passwordMessageId = R.string.error_field_required;
        } else if (!isPasswordValid(password)) {
            passwordMessageId = R.string.error_invalid_password;
        }

        return passwordMessageId;
    }

    public boolean isPasswordValid(String password) {
        return !password.isEmpty() && password.length() >= 8;
    }

    public boolean hasValidCredentialsForLoginAttempt(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }

    public Observable<LoginResponse> performLoginAttempt(String email, String password) {
        return attemptUserLogin.execute(email, password);
    }

    public boolean hasValidSession () {
        return userEmailPref.get() != null && authTokenPref.get() != -1L;
    }
}
