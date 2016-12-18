package com.thompalmer.mocktwitterdemo.data.db.common;

import android.content.ContentValues;

import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;

/*{
    "text": "Ipsum aliquip adipisicing velit consequat qui velit dolor laborum deserunt anim ea. Duis duis ipsum ut magna Lorem non aliquip aute nulla laborum voluptate sunt proident nisi.",
    "replyCount": 20,
    "retweetCount": 84,
    "likeCount": 27,
    "userName": "Brittany Dickson",
    "created_at": "2014-01-30T02:48:41 +05:00",
    "updated_at": "2014-08-17T11:00:31 +04:00",
    "deleted_at": "2016-04-02T06:57:37 +04:00"
}*/
public class SqlTweet {
    public static final String TABLE = "tweet";
    public static final String ID = "id";
    public static final String TEXT = "text";
    public static final String REPLY_COUNT = "reply_count";
    public static final String RETWEET_COUNT = "retweet_count";
    public static final String LIKE_COUNT = "like_count";
    public static final String USER_NAME = "user_name";
    public static final String CREATED_AT = "created_at";
    public static final String UPDATED_AT = "updated_at";
    public static final String DELETED_AT = "deleted_at";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE + " (" +
                    ID + " TEXT PRIMARY KEY, " +
                    TEXT + " TEXT NOT NULL, " +
                    REPLY_COUNT + " INTEGER DEFAULT 0, " +
                    RETWEET_COUNT + " INTEGER DEFAULT 0, " +
                    LIKE_COUNT + " INTEGER DEFAULT 0, " +
                    USER_NAME + " TEXT NOT NULL, " +
                    CREATED_AT + " TEXT NOT NULL, " +
                    UPDATED_AT + " TEXT NOT NULL, " +
                    DELETED_AT + " TEXT DEFAULT NULL)";

    public static final String LIST = "SELECT * FROM " + TABLE +
            " WHERE "  + DELETED_AT + " IS NULL" +
            " ORDER BY " + CREATED_AT + " DESC LIMIT ? ";

    public static final String LIST_WITH_CREATED_AT = "SELECT * FROM " + TABLE +
            " WHERE " + CREATED_AT +  " < ? AND " + DELETED_AT + " IS NULL" +
            " ORDER BY " + CREATED_AT + " LIMIT ? ";

    public static ContentValues build(String text, int replyCount, int retweetCount, int likeCount,
                                      String userName, String createdAt, String updatedAt, String deletedAt) {
        ContentValues values = new ContentValues();
        values.put(TEXT, text);
        values.put(REPLY_COUNT, replyCount);
        values.put(RETWEET_COUNT, retweetCount);
        values.put(LIKE_COUNT, likeCount);
        values.put(USER_NAME, userName);
        values.put(CREATED_AT, createdAt);
        values.put(UPDATED_AT, updatedAt);
        if(deletedAt == null) {
            values.putNull(DELETED_AT);
        } else {
            values.put(DELETED_AT, deletedAt);
        }
        return values;
    }

    public static ContentValues build(Tweet tweet) {
        return build(tweet.text, tweet.replyCount, tweet.retweetCount, tweet.likeCount,
                        tweet.userName, tweet.createdAt, tweet.updatedAt, tweet.deletedAt);
    }
}
