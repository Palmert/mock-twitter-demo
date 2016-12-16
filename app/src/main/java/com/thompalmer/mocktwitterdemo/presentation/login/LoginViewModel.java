package com.thompalmer.mocktwitterdemo.presentation.login;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.view.View;

import com.thompalmer.mocktwitterdemo.R;

public class LoginViewModel {
    private final ObservableField<String> email = new ObservableField<>();
    private final ObservableField<String> password = new ObservableField<>();
    private final ObservableBoolean loggingIn = new ObservableBoolean(false);
    private final LoginViewBinding binding;
    private final LoginPresenter presenter;
    private final Context context;

    LoginViewModel(Context context, LoginViewBinding binding) {
        this.binding = binding;
        this.context = context;
        this.presenter = new LoginPresenter();
    }

    @SuppressWarnings("unused")
    public void onEmailChanged(CharSequence s, int start, int before, int count) {
        email.set(s.toString());
        int emailMessageId = presenter.updateEmailMessageId(email.get());
        binding.textInputLayoutEmail.setError(emailMessageId == 0 ? null : context.getString(emailMessageId));
    }

    @SuppressWarnings("unused")
    public void onPasswordChanged(CharSequence s, int start, int before, int count) {
        password.set(s.toString());
        int passwordMessageId = presenter.updatePasswordMessageId(password.get());
        binding.textInputLayoutPassword.setError(passwordMessageId == 0 ? null : context.getString(passwordMessageId));
    }

    public ObservableBoolean isLoggingIn() {
        return loggingIn;
    }

    public ObservableField<String> getEmail() {
        return email;
    }

    public ObservableField<String> getPassword() {
        return password;
    }

    @SuppressWarnings("unused")
    public void onLoginClicked(View view) {
        if (loggingIn.get()) {
            return;
        }

        if (presenter.shouldValidateCredentials(email.get(), password.get())) {
            loggingIn.set(true);
            presenter.performLoginAttempt(email.get(), password.get())
                    .subscribe(success -> {
                        loggingIn.set(false);
                        if (!success) {
                            binding.textInputLayoutPassword.setError(context.getString(R.string.error_incorrect_password));
                        } else {
                            // TODO Move to next activity
                        }
                    });
        }
    }
}
