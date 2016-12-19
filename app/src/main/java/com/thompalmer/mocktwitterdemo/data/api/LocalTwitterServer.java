package com.thompalmer.mocktwitterdemo.data.api;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Account;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.api.model.request.LoginRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.request.PostTweetRequest;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.LoginResponse;
import com.thompalmer.mocktwitterdemo.data.api.model.response.TweetResponse;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.data.db.common.SqlTweet;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlAccount;
import com.thompalmer.mocktwitterdemo.data.db.server.SqlSession;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.ISODateTimeFormat;

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
    private final @Named(TWITTER_SERVER_DB) DatabaseInteractorImpl db;
    private final RepositoryInteractor<Tweet> tweetRepository;

    public LocalTwitterServer(BehaviorDelegate<TwitterService> delegate, DatabaseInteractorImpl db, RepositoryInteractor<Tweet> tweetRepository) {
        this.delegate = delegate;
        this.db = db;
        this.tweetRepository = tweetRepository;
    }

    @Override
    public Observable<LoginResponse> login(@Body LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        LoginResponse response;

        Cursor cursor = db.query(SqlAccount.QUERY, loginRequest.getEmail());
        try {
            if (cursor.moveToFirst()) {
                Account account = SqlAccount.map(cursor);
                if (account.password.equals(password)) {
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
        String now = ISODateTimeFormat.basicDateTime().print(DateTime.now(DateTimeZone.UTC));
        ContentValues values = SqlSession.build(email, authToken, now, now, null);
        db.insert(SqlSession.TABLE, values, SQLiteDatabase.CONFLICT_REPLACE);
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
            String now = ISODateTimeFormat.basicDateTime().print(DateTime.now(DateTimeZone.UTC));
            tweet.createdAt = now;
            tweet.updatedAt = now;
            db.insert(SqlTweet.TABLE, SqlTweet.build(tweet));
            response = TweetResponse.success(tweet);
        } else {
            response = TweetResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION);
        }

        return delegate.returningResponse(response).postTweet(email, authToken, postTweetRequest);
    }

    private boolean isAuthenticated(String userName, long authToken) {
        Cursor cursor = db.query(SqlSession.QUERY, userName, String.valueOf(authToken));
        return cursor.getCount() == 1;
    }

    private String getUserName(String email) {
        String fullName = email;
        Cursor cursor = db.query(SqlAccount.QUERY, email);
        if (cursor.moveToFirst()) {
            Account account = SqlAccount.map(cursor);
            fullName = account.firstName + " " + account.lastName;
        }
        return fullName;
    }

    @Override
    public Observable<ListTweetsResponse> listTweets(@Header("user_name") String userName, @Header("auth_token") Long authToken,
                                                     @Path("count") String count, @Path("createdAt") String lastCreatedAt) {
        ListTweetsResponse response;
        if (isAuthenticated(userName, authToken)) {
            List<Tweet> tweets = tweetRepository.paginatedList(lastCreatedAt);
            String createdAt = null;
            if (!tweets.isEmpty()) {
                createdAt = tweets.get(tweets.size() - 1).createdAt;
            }
            response = ListTweetsResponse.success(tweets, createdAt);
        } else {
            response = ListTweetsResponse.failure(ERROR_UNAUTHORIZED, MESSAGE_FAILED_AUTHENTICATION);
        }
        return delegate.returningResponse(response).listTweets(userName, authToken, count, lastCreatedAt);
    }
}