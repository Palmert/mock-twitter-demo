package com.thompalmer.mocktwitterdemo.presentation.login;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;

import javax.inject.Inject;

import io.reactivex.Observable;

public class LoginPresenter {
    private final AttemptUserLogin attemptUserLogin;

    @Inject
    public LoginPresenter(AttemptUserLogin attemptUserLogin) {
        this.attemptUserLogin = attemptUserLogin;
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

    public boolean shouldValidateCredentials(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }

    public Observable<LoginResponse> performLoginAttempt(String email, String password) {
        return attemptUserLogin.execute(email, password);
    }
}
