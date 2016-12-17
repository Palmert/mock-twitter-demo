package com.thompalmer.mocktwitterdemo.base;

import android.content.Context;

import com.thompalmer.mocktwitterdemo.TwitterApp;
import com.thompalmer.mocktwitterdemo.TwitterComponent;

public abstract class BaseViewModel {
    protected TwitterComponent baseComponent;

    public BaseViewModel(Context context) {
        baseComponent = TwitterApp.get(context).component();
        buildComponentAndInject();
    }

    protected abstract void buildComponentAndInject();
}
