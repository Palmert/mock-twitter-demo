package com.thompalmer.mocktwitterdemo.base;

import android.content.Context;

import com.thompalmer.mocktwitterdemo.MockTwitterApp;
import com.thompalmer.mocktwitterdemo.MockTwitterComponent;

public abstract class BaseViewModel {
    protected MockTwitterComponent baseComponent;

    public BaseViewModel(Context context) {
        baseComponent = MockTwitterApp.get(context).component();
        buildComponentAndInject();
    }

    protected abstract void buildComponentAndInject();
}
