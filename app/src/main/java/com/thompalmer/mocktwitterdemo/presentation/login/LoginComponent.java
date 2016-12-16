package com.thompalmer.mocktwitterdemo.presentation.login;

import com.thompalmer.mocktwitterdemo.MockTwitterComponent;

import dagger.Component;

@LoginScope
@Component(dependencies = MockTwitterComponent.class)
public interface LoginComponent {
    void inject(LoginViewModel viewModel);
}
