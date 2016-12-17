package com.thompalmer.mocktwitterdemo.data.sharedpreference;

import android.content.SharedPreferences;

import javax.inject.Inject;

public class LongPreference implements SharePreferenceWrapper<Long> {
    private final SharedPreferences preferences;
    private final String key;
    private final Long defaultValue;

    @Inject
    public LongPreference(SharedPreferences preferences, String key, Long defaultValue) {
        this.preferences = preferences;
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public Long get() {
        return preferences.getLong(key, defaultValue);
    }

    @Override
    public void set(Long value) {
        preferences.edit().putLong(key, value).apply();
    }

    @Override
    public void remove() {
        preferences.edit().remove(key).apply();
    }
}