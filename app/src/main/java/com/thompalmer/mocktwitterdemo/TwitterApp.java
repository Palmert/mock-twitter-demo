package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.Context;

public class TwitterApp extends Application {
    private TwitterComponent component;

    public static TwitterApp get(Context context) {
        return (TwitterApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
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
