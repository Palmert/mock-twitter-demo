package com.thompalmer.mocktwitterdemo.domain;

public interface UserSessionPersister {
    void save(String email, Long authToken);
    String getEmail();
    Long getAuthToken();
    void clear();
    boolean hasValidSession();
}
