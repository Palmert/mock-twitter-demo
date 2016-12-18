package com.thompalmer.mocktwitterdemo;

import android.app.Application;
import android.content.Context;

import com.facebook.stetho.Stetho;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

public class TwitterApplication extends Application {
    @Inject TwitterServerDatabase twitterServerDatabase;
    private TwitterComponent component;


    public static TwitterApplication get(Context context) {
        return (TwitterApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
        Stetho.initializeWithDefaults(this);
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
