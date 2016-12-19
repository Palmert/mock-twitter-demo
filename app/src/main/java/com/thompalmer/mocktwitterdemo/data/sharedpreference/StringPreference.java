package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class StringPreference implements SharePreferenceWrapper<String> {
    private final SharedPreferences preferences;
    private final String key;
    private final String defaultValue;

    @Inject
    public StringPreference(SharedPreferences preferences, String key, String defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public String get() {
        return preferences.getString(key, defaultValue);
    }

    @Override
    public void set(String value) {
        preferences.edit().putString(key, value).apply();
    }

    @Override
    public void clear() {
        preferences.edit().remove(key).apply();
    }
}