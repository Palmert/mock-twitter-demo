package com.thompalmer.mocktwitterdemo.domain;

public interface UserSessionPersister {
    void save(String email, Long authToken);
    String getEmail();
    Long getAuthTokenPref();
    void clear();
    boolean hasValidSession();
}
