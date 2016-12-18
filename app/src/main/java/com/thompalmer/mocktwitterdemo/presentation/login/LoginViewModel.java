package com.thompalmer.mocktwitterdemo.presentation.login;

import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableBoolean;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;
import com.thompalmer.mocktwitterdemo.presentation.feed.FeedActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

@LoginScope
public class LoginViewModel extends BaseViewModel {
    private final ObservableBoolean loggingIn = new ObservableBoolean(false);
    private final LoginViewBinding viewBinding;
    private final Context context;
    private String email;
    private String password;

    @Inject LoginPresenter presenter;

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
                                    displayRetry();
                                } else {
                                    Intent intent = new Intent(context, FeedActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                    context.startActivity(intent);
                                }
                            }, throwable -> displayRetry()
                    );
        }
    }

    private void displayRetry() {
        Snackbar.make(viewBinding.getRoot(), "Unable to login", Snackbar.LENGTH_LONG)
                .setAction("Retry", this::onLoginClicked)
                .show();
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
