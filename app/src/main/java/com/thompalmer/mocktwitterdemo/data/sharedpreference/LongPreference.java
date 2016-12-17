package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class LongPreference {
    private final SharedPreferences preferences;
    private final String key;
    private final Long defaultValue;

    @Inject
    public LongPreference(SharedPreferences preferences, String key, Long defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    public long get() {
        return preferences.getLong(key, defaultValue);
    }

    public void set(long value) {
        preferences.edit().putLong(key, value).apply();
    }
}