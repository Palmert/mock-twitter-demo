package com.thompalmer.mocktwitterdemo.domain.interactor;

public interface UserSessionInteractor {
    void save(String email, Long authToken);
    String getEmail();
    Long getAuthToken();
    void clear();
    boolean hasValidSession();
}
