package com.thompalmer.mocktwitterdemo.presentation.feed;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.TwitterApplication;
import com.thompalmer.mocktwitterdemo.presentation.login.LoginActivity;

import javax.inject.Inject;

public class FeedActivity extends AppCompatActivity {
    public static final String EXTRA_CREATED_AT = "createdAt";
    @Inject FeedPresenter presenter;
    private FeedViewModel feedViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
        FeedViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_feed);
        feedViewModel = new FeedViewModel(this, binding);
        binding.setViewModel(feedViewModel);
        setSupportActionBar(binding.toolbar);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tweet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.logout) {
            presenter.onLogoutPressed();
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void buildComponentAndInject() {
        DaggerFeedComponent.builder()
                .twitterComponent(TwitterApplication.get(this).component())
                .build()
                .inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(FeedViewModel.REQUEST_CODE_TWEET == requestCode && RESULT_OK == resultCode) {
            feedViewModel.displayLatestTweet(data.getStringExtra(EXTRA_CREATED_AT));
        }
    }
}
