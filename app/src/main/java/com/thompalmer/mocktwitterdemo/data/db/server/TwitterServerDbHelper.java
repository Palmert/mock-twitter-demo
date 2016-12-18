package com.thompalmer.mocktwitterdemo.data.db.server;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;

import java.io.IOException;
import java.io.InputStream;

public class TwitterServerDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TwitterServer.db";
    private final Context context;

    public TwitterServerDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SqlTweet.CREATE_TABLE);
        Log.d("Database", "Creating database");
        ListTweetsResponse listTweetsResponse =
                new GsonBuilder().create().fromJson(loadJSONFromAsset("tweets.json"), ListTweetsResponse.class);
        try {
            db.beginTransaction();
            for(Tweet tweet : listTweetsResponse.tweets) {
                db.insert(SqlTweet.TABLE, null, SqlTweet.build(tweet));
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        db.execSQL(SqlAccount.CREATE_TABLE);
        db.execSQL(SqlSession.CREATE_TABLE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public String loadJSONFromAsset(String assetName) {
        String json = null;
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
