package com.thompalmer.mocktwitterdemo.domain.interactor;

import java.util.List;

public interface RepositoryInteractor<T> {
    List<T> paginatedList(String lastCreatedAt);
    T get(String primaryKey);
    void remove(String primaryKey);
    void clear();
    void save(T item);
    void saveAll(List<T> items, String lastCreatedAt);
}
