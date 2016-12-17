package com.thompalmer.mocktwitterdemo.presentation.login;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@LoginScope
public class LoginViewModel extends BaseViewModel {
    private final ObservableBoolean loggingIn = new ObservableBoolean(false);
    private final LoginViewBinding viewBinding;
    private final Context context;
    @Inject LoginPresenter presenter;
    private String email;
    private String password;

    public LoginViewModel(Context context, LoginViewBinding viewBinding) {
        super(context);
        this.context = context;
        this.viewBinding = viewBinding;
    }

    @Override
    protected void buildComponentAndInject() {
        DaggerLoginComponent.builder()
                .twitterComponent(baseComponent)
                .build()
                .inject(this);
    }

    @SuppressWarnings("unused")
    public void onEmailChanged(CharSequence s, int start, int before, int count) {
        email = s.toString();
        int emailMessageId = presenter.updateEmailMessageId(email);
        viewBinding.textInputLayoutEmail.setError(emailMessageId == 0 ? null : context.getString(emailMessageId));
    }

    @SuppressWarnings("unused")
    public void onPasswordChanged(CharSequence s, int start, int before, int count) {
        password = s.toString();
        int passwordMessageId = presenter.updatePasswordMessageId(password);
        viewBinding.textInputLayoutPassword.setError(passwordMessageId == 0 ? null : context.getString(passwordMessageId));
    }

    @SuppressWarnings("unused")
    public void onLoginClicked(View view) {
        if (loggingIn.get()) {
            return;
        }

        if (presenter.hasValidCredentialsForLoginAttempt(email, password)) {
            loggingIn.set(true);
            presenter.performLoginAttempt(email, password)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(loginResponse -> {
                        loggingIn.set(false);
                        if (loginResponse.error != null) {
                            viewBinding.textInputLayoutPassword.setError(loginResponse.error.message);
                        } else {
                            // TODO Move to next activity
                        }
                    });
        }
    }

    public ObservableBoolean isLoggingIn() {
        return loggingIn;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}