package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.Context;

import net.danlew.android.joda.JodaTimeAndroid;

public class TwitterApplication extends Application {
    private TwitterComponent component;

    public static TwitterApplication get(Context context) {
        return (TwitterApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        buildComponentAndInject();
    }

    private void buildComponentAndInject() {
        component = TwitterComponent.Initializer.init(this);
        component.inject(this);
    }

    public TwitterComponent component() {
        return component;
    }
}
