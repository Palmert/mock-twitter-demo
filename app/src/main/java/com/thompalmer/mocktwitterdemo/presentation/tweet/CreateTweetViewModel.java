package com.thompalmer.mocktwitterdemo.presentation.tweet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.ObservableInt;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;
import com.thompalmer.mocktwitterdemo.presentation.feed.FeedActivity;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static android.app.Activity.*;

@CreateTweetScope
public class CreateTweetViewModel extends BaseViewModel {
    private final Context context;
    private String tweetContent;
    private ObservableInt characterCount = new ObservableInt();
    private boolean creatingTweet;


@Inject CreateTweetPresenter presenter;

    public CreateTweetViewModel(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void buildComponentAndInject() {
        DaggerCreateTweetComponent.builder()
                .twitterComponent(baseComponent)
                .build()
                .inject(this);
    }

    @SuppressWarnings("unused")
    public void onTweetContentChanged(CharSequence s, int start, int before, int count) {
        tweetContent = s.toString();
        characterCount.set(s.length());
    }

    @SuppressWarnings("unused")
    public void onPostTweetClicked(View view) {
        if(!creatingTweet) {
            creatingTweet = true;
            presenter.onPostTweetClicked(tweetContent)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(tweetResponse -> {
                        if (tweetResponse.error == null) {
                            Intent intent = new Intent();
                            intent.putExtra(FeedActivity.EXTRA_CREATED_AT, tweetResponse.tweet.createdAt);
                            ((Activity) context).setResult(RESULT_OK, intent);
                            ((Activity) context).finish();
                        }
                    });
        }
    }

    public ObservableInt getCharacterCount() {
        return characterCount;
    }
}
