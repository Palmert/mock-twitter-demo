package com.thompalmer.mocktwitterdemo.domain.interactor;

import java.util.List;

import io.reactivex.Observable;

public interface RepositoryInteractor<T> {
    List<T> list();
    Observable<List<T>> listObservable();
    List<T> paginatedList(String lastCreatedAt);
    Observable<List<T>> paginatedListObservable(String lastCreatedAt);
    T get(String primaryKey);
    void remove(String primaryKey);
    void clear();
    void save(T item);
    void saveAll(List<T> items);
}
