package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class StringPreference {
    private final SharedPreferences preferences;
    private final String key;

    @Inject
    public StringPreference(SharedPreferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    public String get(String defaultValue) {
        return preferences.getString(key, defaultValue);
    }

    public void set(String value) {
        preferences.edit().putString(key, value).apply();
    }
}