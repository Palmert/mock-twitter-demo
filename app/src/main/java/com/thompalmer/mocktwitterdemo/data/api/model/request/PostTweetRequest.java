package com.thompalmer.mocktwitterdemo.data.api.model.request;

public class PostTweetRequest {
    private final String text;

    private PostTweetRequest(String text) {
        this.text = text;
    }

    public static PostTweetRequest create(String text) {
        return new PostTweetRequest(text);
    }

    public String getText() {
        return text;
    }
}
