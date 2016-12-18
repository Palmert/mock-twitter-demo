package com.thompalmer.mocktwitterdemo.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlAccount;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;
import com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Random;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;

public final class LocalTwitterServer implements TwitterService {
    private static final int ERROR_UNAUTHORIZED = 401;
    private static final String MESSAGE_INVALID_PASSWORD = "Invalid username/password";
    private static final String MESSAGE_USER_DOES_NOT_EXIST = "Username does not exist";
    private final BehaviorDelegate<TwitterService> delegate;
    private final TwitterServerDatabase db;

    public LocalTwitterServer(BehaviorDelegate<TwitterService> delegate, TwitterServerDatabase db) {
        this.delegate = delegate;
        this.db = db;
    }

    @Override
    public Observable<LoginResponse> login(@Body LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        LoginResponse response;

        Cursor cursor = db.get().query(SqlAccount.QUERY, loginRequest.getEmail());
        try {
            if(cursor.moveToFirst()) {
                String userPassword = cursor.getString(cursor.getColumnIndex(SqlAccount.PASSWORD));
                if(userPassword.equals(password)) {
                    Long authToken = storeSessionInfo(email);
                    response = LoginResponse.success(email, authToken);
                } else {
                    response = LoginResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_INVALID_PASSWORD);
                }
            } else {
                response = LoginResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_USER_DOES_NOT_EXIST);
            }
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }

        return delegate.returningResponse(response).login(loginRequest);
    }

    @NonNull
    private Long storeSessionInfo(String email) {
        Long authToken = new Random().nextLong();
        String now = DateTime.now().toString();
        ContentValues values = SqlSession.build(email, authToken, now, now, null);
        db.get().insert(SqlSession.TABLE, values);
        return authToken;
    }

    @Override
    public Observable<ListTweetsResponse> listTweets(@Path("count") String count, @Path("createdAt") String lastCreatedAt) {
        Cursor cursor;
        if (lastCreatedAt == null) {
            cursor = db.get().query(SqlTweet.LIST, count);
        } else {
            cursor = db.get().query(SqlTweet.LIST_WITH_CREATED_AT, lastCreatedAt, count);
        }

        ListTweetsResponse listTweetsResponse = new ListTweetsResponse();
        listTweetsResponse.tweets = new ArrayList<>();

        try {
            while (cursor.moveToNext()) {
                Tweet tweet = new Tweet();
                tweet.text = cursor.getString(cursor.getColumnIndex(SqlTweet.TEXT));
                tweet.replyCount = cursor.getInt(cursor.getColumnIndex(SqlTweet.REPLY_COUNT));
                tweet.retweetCount = cursor.getInt(cursor.getColumnIndex(SqlTweet.RETWEET_COUNT));
                tweet.likeCount = cursor.getInt(cursor.getColumnIndex(SqlTweet.LIKE_COUNT));
                tweet.userName = cursor.getString(cursor.getColumnIndex(SqlTweet.USER_NAME));
                tweet.createdAt = cursor.getString(cursor.getColumnIndex(SqlTweet.CREATED_AT));
                tweet.updatedAt = cursor.getString(cursor.getColumnIndex(SqlTweet.UPDATED_AT));
                tweet.deletedAt = cursor.getString(cursor.getColumnIndex(SqlTweet.DELETED_AT));

                listTweetsResponse.tweets.add(tweet);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return delegate.returningResponse(listTweetsResponse).listTweets(count, lastCreatedAt);
    }
}