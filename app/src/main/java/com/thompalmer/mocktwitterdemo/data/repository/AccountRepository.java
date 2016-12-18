package com.thompalmer.mocktwitterdemo.data.repository;

import android.database.Cursor;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Account;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlAccount;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import kotlin.NotImplementedError;

public class AccountRepository implements RepositoryInteractor<Account> {
    private final DatabaseInteractorImpl db;

    @Inject
    public AccountRepository(DatabaseInteractorImpl db) {
        this.db = db;
    }

    @Override
    public List<Account> list() {
        throw new NotImplementedError("List accounts not implemented");
    }

    @Override
    public Observable<List<Account>> listObservable() {
        throw new NotImplementedError("Observable list accounts not implemented");
    }

    @Override
    public List<Account> paginatedList(String lastCreatedAt) {
        throw new NotImplementedError("Paginated list accounts not implemented");
    }

    @Override
    public Observable<List<Account>> paginatedListObservable(String lastCreatedAt) {
        throw new NotImplementedError("Observable paginated list accounts not implemented");

    }

    @Override
    public Account get(String email) {
        Account account = null;
        Cursor cursor = db.query(SqlAccount.QUERY, email);
        if(cursor.moveToFirst()) {
            account = SqlAccount.map(cursor);
        }
        return account;
    }

    @Override
    public void remove(String primaryKey) {
        //No op
    }

    @Override
    public void clear() {
        db.delete(SqlAccount.TABLE, null);
    }

    @Override
    public void save(Account item) {
        db.insert(SqlAccount.TABLE, SqlAccount.build(item));
    }

    @Override
    public void saveAll(List<Account> items) {
        BriteDatabase.Transaction transaction = db.newTransaction();
        try {
            for (Account account : items) {
                save(account);
            }
            transaction.markSuccessful();
        }finally {
            transaction.end();
        }

    }
}
