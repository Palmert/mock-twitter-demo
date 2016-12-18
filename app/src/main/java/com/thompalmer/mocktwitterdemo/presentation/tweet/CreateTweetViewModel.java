package com.thompalmer.mocktwitterdemo.presentation.tweet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.databinding.ObservableInt;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;

import javax.inject.Inject;

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

    @SuppressWarnings("unused")
    public void onPostTweetClicked(View view) {
        presenter.onPostTweetClicked(tweetContent);
    }

    public ObservableInt getCharacterCount() {
        return characterCount;
    }
}
