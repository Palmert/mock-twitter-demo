package com.thompalmer.mocktwitterdemo.presentation.tweet;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.TwitterApplication;

import javax.inject.Inject;

public class CreateTweetActivity extends AppCompatActivity {
    @Inject CreateTweetPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
        CreateTweetViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_tweet);
        binding.setViewModel(new CreateTweetViewModel(this, binding));
        setSupportActionBar(binding.toolbar);
    }

    private void buildComponentAndInject() {
        DaggerCreateTweetComponent.builder()
                .twitterComponent(TwitterApplication.get(this).component())
                .build()
                .inject(this);
    }
}
