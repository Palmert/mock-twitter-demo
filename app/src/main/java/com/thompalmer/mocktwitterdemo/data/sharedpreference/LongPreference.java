package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class LongPreference {
    private final SharedPreferences preferences;
    private final String key;

    @Inject
    public LongPreference(SharedPreferences preferences, String key) {
        this.preferences = preferences;
        this.key = key;
    }

    public long get(long defaultValue) {
        return preferences.getLong(key, defaultValue);
    }

    public void set(long value) {
        preferences.edit().putLong(key, value).apply();
    }
}