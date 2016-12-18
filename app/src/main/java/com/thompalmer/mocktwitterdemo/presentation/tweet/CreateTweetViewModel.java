package com.thompalmer.mocktwitterdemo.presentation.tweet;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;
import com.thompalmer.mocktwitterdemo.presentation.feed.DaggerFeedComponent;
import com.thompalmer.mocktwitterdemo.presentation.feed.FeedViewBinding;

import javax.inject.Inject;

import rx.Observable;

@CreateTweetScope
public class CreateTweetViewModel extends BaseViewModel {
    private final Context context;
    private final CreateTweetViewBinding viewBinding;
    private String tweetContent;
    private ObservableInt characterCount = new ObservableInt();

    @Inject CreateTweetPresenter presenter;

    public CreateTweetViewModel(Context context, CreateTweetViewBinding viewBinding) {
        super(context);
        this.context = context;
        this.viewBinding = viewBinding;
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

    public void onTweetClicked(View view) {
        presenter.onTweetClicked(tweetContent);
    }

    public ObservableInt getCharacterCount() {
        return characterCount;
    }
}