package com.thompalmer.mocktwitterdemo.presentation.feed;

import android.content.Context;
import android.databinding.ObservableBoolean;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.base.BaseViewModel;
import com.thompalmer.mocktwitterdemo.data.api.model.response.ListTweetsResponse;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@FeedScope
public class FeedViewModel extends BaseViewModel {
    private final ObservableBoolean loggingIn = new ObservableBoolean(false);
    private final Context context;
    private final FeedViewBinding viewBinding;
    private final FeedAdapter adapter;

    @Inject FeedPresenter presenter;

    public FeedViewModel(Context context, FeedViewBinding viewBinding) {
        super(context);
        this.context = context;
        this.viewBinding = viewBinding;
        adapter = new FeedAdapter();
        viewBinding.recyclerviewTweets.setLayoutManager(new LinearLayoutManager(context));
        viewBinding.recyclerviewTweets.setAdapter(adapter);
        presenter.listTweets()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listTweetsResponse -> {
                    Log.d("Total tweets", String.valueOf(listTweetsResponse.tweets.size()));
                    adapter.setFeedItems(listTweetsResponse.tweets);
                });
    }

    public void onAddTweetClicked(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    protected void buildComponentAndInject() {
        DaggerFeedComponent.builder()
                .twitterComponent(baseComponent)
                .build()
                .inject(this);
    }
}
