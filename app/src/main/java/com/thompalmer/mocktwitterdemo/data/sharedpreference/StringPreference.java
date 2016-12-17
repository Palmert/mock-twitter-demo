package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class StringPreference {
    private final SharedPreferences preferences;
    private final String key;
    private final String defaultValue;

    @Inject
    public StringPreference(SharedPreferences preferences, String key, String defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public String get() {
        return preferences.getString(key, defaultValue);
    }

    public void set(String value) {
        preferences.edit().putString(key, value).apply();
    }
}