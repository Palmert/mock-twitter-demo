package com.thompalmer.mocktwitterdemo.data.sharedpreference;

/**
 * Created by thompalmer on 2016-12-17.
 */

public interface SharePreferenceWrapper<T> {
    T get();
    void set(T value);
    void remove();
}
