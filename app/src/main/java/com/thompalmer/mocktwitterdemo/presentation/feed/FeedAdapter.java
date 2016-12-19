package com.thompalmer.mocktwitterdemo.presentation.feed;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thompalmer.mocktwitterdemo.BR;
import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.databinding.FeedItemBinding;

import java.util.Collections;
import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<Tweet> feedItems = Collections.emptyList();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Tweet tweet = feedItems.get(position);
        holder.getBinding().setVariable(BR.tweet, tweet);
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public void setFeedItems(List<Tweet> feedItems) {
        if(this.feedItems.isEmpty()) {
            this.feedItems = feedItems;
            notifyDataSetChanged();
        } else {
            this.feedItems.addAll(feedItems);
            notifyItemRangeInserted(this.feedItems.size() - feedItems.size(), feedItems.size());
        }
    }

    public String getLastCreatedAt() {
        return feedItems.isEmpty() ? null : feedItems.get(feedItems.size() - 1).createdAt;
    }

    public void addTweet(Tweet tweet) {
        feedItems.add(0, tweet);
        notifyItemInserted(0);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private FeedItemBinding feedItemBinding;

        public ViewHolder(View itemView) {
            super(itemView);
            feedItemBinding = DataBindingUtil.bind(itemView);
        }

        public FeedItemBinding getBinding() {
            return feedItemBinding;
        }
    }
}
