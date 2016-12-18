package com.thompalmer.mocktwitterdemo.presentation.feed;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;
import com.thompalmer.mocktwitterdemo.presentation.tweet.CreateTweetActivity;

import javax.inject.Inject;

@FeedScope
public class FeedViewModel extends BaseViewModel {
    private final Context context;
    private final FeedViewBinding viewBinding;
    private final FeedAdapter adapter;

    @Inject FeedPresenter presenter;

    public FeedViewModel(Context context, FeedViewBinding viewBinding) {
        super(context);
        this.context = context;
        this.viewBinding = viewBinding;
        adapter = new FeedAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        viewBinding.recyclerviewTweets.setLayoutManager(linearLayoutManager);
        viewBinding.recyclerviewTweets.setAdapter(adapter);
        viewBinding.recyclerviewTweets.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() - adapter.getItemCount() < 15) {
                    presenter.listTweets(adapter);
                }
            }
        });
        presenter.listTweets(adapter);
    }

    public void onAddTweetClicked(View view) {
        Intent intent = new Intent(context, CreateTweetActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void buildComponentAndInject() {
        DaggerFeedComponent.builder()
                .twitterComponent(baseComponent)
                .build()
                .inject(this);
    }
}
