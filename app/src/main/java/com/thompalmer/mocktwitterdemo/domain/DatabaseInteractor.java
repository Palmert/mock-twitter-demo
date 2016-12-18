package com.thompalmer.mocktwitterdemo.domain;


import com.squareup.sqlbrite.BriteDatabase;

public interface DatabaseInteractor {
    BriteDatabase get();
}
