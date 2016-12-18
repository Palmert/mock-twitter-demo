package com.thompalmer.mocktwitterdemo.data.db.server;

import android.content.ContentValues;

public class SqlAccount {
    public static final String TABLE = "account";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";


    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + " (" +
                    EMAIL + " TEXT PRIMARY KEY, " +
                    PASSWORD + " TEXT NOT NULL, " +
                    CREATED_AT + " TEXT NOT NULL, " +
                    UPDATED_AT + " TEXT NOT NULL, " +
                    DELETED_AT + " TEXT DEFAULT NULL)";

    public static final String QUERY = "SELECT * FROM " + TABLE +
            " WHERE " + EMAIL + " = ? AND " + DELETED_AT + " IS NULL";


    public static ContentValues build(String email, String password, String createdAt, String updatedAt, String deletedAt) {
        ContentValues values = new ContentValues();
        values.put(EMAIL, email);
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
}
