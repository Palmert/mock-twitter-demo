package com.thompalmer.mocktwitterdemo.data.db.server;

import android.content.ContentValues;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Account;

public class SqlAccount {
    public static final String TABLE = "account";
    public static final String EMAIL = "email";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String PASSWORD = "password";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + " (" +
                    EMAIL + " TEXT PRIMARY KEY, " +
                    FIRST_NAME + " TEXT NOT NULL, " +
                    LAST_NAME + " TEXT NOT NULL, " +
                    PASSWORD + " TEXT NOT NULL, " +
                    CREATED_AT + " TEXT NOT NULL, " +
                    UPDATED_AT + " TEXT NOT NULL, " +
                    DELETED_AT + " TEXT DEFAULT NULL)";

    public static final String QUERY = "SELECT * FROM " + TABLE +
            " WHERE " + EMAIL + " = ? AND " + DELETED_AT + " IS NULL";


    public static ContentValues build(String email, String firstName, String lastName, String password, String createdAt, String updatedAt, String deletedAt) {
        ContentValues values = new ContentValues();
        values.put(EMAIL, email);
        values.put(FIRST_NAME, firstName);
        values.put(LAST_NAME, lastName);
        values.put(PASSWORD, password);
        values.put(CREATED_AT, createdAt);
        values.put(UPDATED_AT, updatedAt);
        if(deletedAt == null) {
            values.putNull(DELETED_AT);
        } else {
            values.put(DELETED_AT, deletedAt);
        }
        return values;
    }

    public static ContentValues build(Account account) {
        return build(account.email, account.firstName, account.lastName, account.password, account.createdAt, account.updatedAt, account.deletedAt);
    }
}
