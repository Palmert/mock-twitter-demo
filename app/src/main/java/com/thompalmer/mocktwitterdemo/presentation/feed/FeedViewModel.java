package com.thompalmer.mocktwitterdemo.presentation.feed;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.thompalmer.mocktwitterdemo.base.BaseViewModel;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

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
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition() - adapter.getItemCount() < 15) {
                    presenter.listTweets(adapter);
                }
            }
        });
        presenter.listTweets(adapter);
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
