package com.thompalmer.mocktwitterdemo.presentation.login;

import com.thompalmer.mocktwitterdemo.TwitterComponent;

import dagger.Component;

@LoginScope
@Component(dependencies = TwitterComponent.class)
public interface LoginComponent {
    void inject(LoginViewModel viewModel);
    void inject(LoginActivity activity);
}
