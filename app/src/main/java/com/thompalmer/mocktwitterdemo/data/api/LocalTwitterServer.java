package com.thompalmer.mocktwitterdemo.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.squareup.sqlbrite.BriteDatabase;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlAccount;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Named;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.mock.BehaviorDelegate;

import static com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase.*;

public class LocalTwitterServer implements TwitterService {
    public static final int ERROR_UNAUTHORIZED = 401;
    public static final String MESSAGE_INVALID_PASSWORD = "Invalid username/password";
    public static final String MESSAGE_USER_DOES_NOT_EXIST = "Username does not exist";
    public static final String MESSAGE_FAILED_AUTHENTICATION = "Failed Authentication";
    private final BehaviorDelegate<TwitterService> delegate;
    private final @Named(TWITTER_SERVER_DB) BriteDatabase db;

    public LocalTwitterServer(BehaviorDelegate<TwitterService> delegate, BriteDatabase db) {
        this.delegate = delegate;
        this.db = db;
    }

    @Override
    public Observable<LoginResponse> login(@Body LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        LoginResponse response;

        Cursor cursor = db.query(SqlAccount.QUERY, loginRequest.getEmail());
        try {
            if (cursor.moveToFirst()) {
                String userPassword = cursor.getString(cursor.getColumnIndex(SqlAccount.PASSWORD));
                if (userPassword.equals(password)) {
                    Long authToken = storeSessionInfo(email);
                    response = LoginResponse.success(email, authToken);
                } else {
                    response = LoginResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_INVALID_PASSWORD);
                }
            } else {
                response = LoginResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_USER_DOES_NOT_EXIST);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return delegate.returningResponse(response).login(loginRequest);
    }

    @NonNull
    private Long storeSessionInfo(String email) {
        Long authToken = new Random().nextLong();
        String now = DateTime.now(DateTimeZone.UTC).toString();
        ContentValues values = SqlSession.build(email, authToken, now, now, null);
        db.insert(SqlSession.TABLE, values);
        return authToken;
    }

    @Override
    public Observable<TweetResponse> postTweet(@Header("user_name") String email, @Header("auth_token") Long authToken,
                                               @Body PostTweetRequest postTweetRequest) {
        TweetResponse response;
        if (isAuthenticated(email, authToken)) {
            Tweet tweet = new Tweet();
            tweet.userName = getUserName(email);
            tweet.text = postTweetRequest.getText();
            String now = DateTime.now(DateTimeZone.UTC).toString();
            tweet.createdAt = now;
            tweet.updatedAt = now;
            db.insert(SqlTweet.TABLE, SqlTweet.build(tweet));
            response = TweetResponse.success(tweet);
        } else {
            response = TweetResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION);
        }

        return delegate.returningResponse(response).postTweet(email, authToken, postTweetRequest);
    }

    private boolean isAuthenticated(    String userName, long authToken) {
        Cursor cursor = db.query(SqlSession.QUERY, userName, String.valueOf(authToken));
        return cursor.getCount() == 1;
    }

    private String getUserName( String email) {
        String fullName = email;
        Cursor cursor = db.query(SqlAccount.QUERY, email);
        if(cursor.moveToFirst()) {
            String firstName = cursor.getString(cursor.getColumnIndex(SqlAccount.FIRST_NAME));
            String lastName = cursor.getString(cursor.getColumnIndex(SqlAccount.LAST_NAME));
            fullName =  firstName + " " + lastName;
        }
        return fullName;
    }

    @Override
    public Observable<ListTweetsResponse> listTweets(@Header("user_name") String userName, @Header("auth_token") Long authToken,
                                                     @Path("count") String count, @Path("createdAt") String lastCreatedAt) {
        ListTweetsResponse response;
        Cursor cursor;
        if (isAuthenticated(userName, authToken)) {
            if (lastCreatedAt == null) {
                cursor = db.query(SqlTweet.LIST, count);
            } else {
                cursor = db.query(SqlTweet.LIST_WITH_CREATED_AT, lastCreatedAt, count);
            }

            List<Tweet> tweets = new ArrayList<>();
            String createdAt = null;
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

                    tweets.add(tweet);
                    createdAt = tweet.createdAt;
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            response = ListTweetsResponse.success(tweets, createdAt);
        } else {
            response = ListTweetsResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION);
        }
        return delegate.returningResponse(response).listTweets(userName, authToken, count, lastCreatedAt);
    }
}