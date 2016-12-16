package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.Context;

public class MockTwitterApp extends Application {
    private MockTwitterComponent component;

    public static MockTwitterApp get(Context context) {
        return (MockTwitterApp) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        buildComponentAndInject();
    }

    public void buildComponentAndInject() {
        component = MockTwitterComponent.Initializer.init(this);
        component.inject(this);
    }

    public MockTwitterComponent component() {
        return component;
    }
}
