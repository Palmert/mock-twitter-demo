package com.thompalmer.mocktwitterdemo.data.db.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Account;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class TwitterServerDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TwitterServer.db";
    private final Context context;

    public TwitterServerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlTweet.CREATE_TABLE);
        db.execSQL(SqlAccount.CREATE_TABLE);
        db.execSQL(SqlSession.CREATE_TABLE);

        Gson gson = new GsonBuilder().create();
        seedAccounts(db, gson);
        seedTweets(db, gson);

    }

    private void seedTweets(SQLiteDatabase db, Gson gson) {
        Type type = new TypeToken<ArrayList<Tweet>>() {}.getType();
        List<Tweet> tweets = gson.fromJson(loadJSONFromAsset("tweets.json"), type);
        try {
            db.beginTransaction();
            for(Tweet tweet : tweets) {
                db.insert(SqlTweet.TABLE, null, SqlTweet.build(tweet));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    private void seedAccounts(SQLiteDatabase db, Gson gson) {
        Type type = new TypeToken<ArrayList<Account>>() {}.getType();
        List<Account> accounts = gson.fromJson(loadJSONFromAsset("accounts.json"), type);
        try {
            db.beginTransaction();
            for(Account account : accounts) {
                db.insert(SqlAccount.TABLE, null, SqlAccount.build(account));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrades
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    private String loadJSONFromAsset(String assetName) {
        String json;
        try {
            InputStream is = context.getAssets().open(assetName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
