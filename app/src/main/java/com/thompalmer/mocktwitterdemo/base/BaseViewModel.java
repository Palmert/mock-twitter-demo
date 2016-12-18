package com.thompalmer.mocktwitterdemo.base;

import android.content.Context;

import com.thompalmer.mocktwitterdemo.TwitterApplication;
import com.thompalmer.mocktwitterdemo.TwitterComponent;

public abstract class BaseViewModel {
    protected TwitterComponent baseComponent;

    public BaseViewModel(Context context) {
        baseComponent = TwitterApplication.get(context).component();
        buildComponentAndInject();
    }

    protected abstract void buildComponentAndInject();
}
