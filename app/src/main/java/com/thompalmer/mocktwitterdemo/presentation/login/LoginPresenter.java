package com.thompalmer.mocktwitterdemo.presentation.login;

import android.text.TextUtils;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.domain.AttemptUserLogin;

import javax.inject.Inject;

import io.reactivex.Observable;
import retrofit2.Response;

public class LoginPresenter {
    private final AttemptUserLogin attemptUserLogin;

    @Inject
    public LoginPresenter(AttemptUserLogin attemptUserLogin) {
        this.attemptUserLogin = attemptUserLogin;
    }

    public int updateEmailMessageId(String email) {
        int emailMessageId = 0;

        if (TextUtils.isEmpty(email)) {
            emailMessageId = R.string.error_field_required;
        } else if (!isEmailValid(email)) {
            emailMessageId = R.string.error_invalid_email;
        }

        return emailMessageId;
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && email.contains("@");
    }

    public int updatePasswordMessageId(String password) {
        int passwordMessageId = 0;

        if (TextUtils.isEmpty(password)) {
            passwordMessageId = R.string.error_field_required;
        } else if (!isPasswordValid(password)) {
            passwordMessageId = R.string.error_invalid_password;
        }

        return passwordMessageId;
    }

    public boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 8;
    }

    public boolean shouldValidateCredentials(String email, String password) {
        return isEmailValid(email) && isPasswordValid(password);
    }

    public Observable<LoginResponse> performLoginAttempt(String email, String password) {
        return attemptUserLogin.execute(email, password);
    }
}
