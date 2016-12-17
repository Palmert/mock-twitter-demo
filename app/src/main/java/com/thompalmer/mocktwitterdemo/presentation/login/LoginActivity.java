package com.thompalmer.mocktwitterdemo.presentation.login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.thompalmer.mocktwitterdemo.R;
import com.thompalmer.mocktwitterdemo.TwitterApp;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.HasCurrentSession;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.StringPreference;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserEmailPref;
import com.thompalmer.mocktwitterdemo.presentation.feed.TweetActivity;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity {
    @Inject @HasCurrentSession Boolean hasCurrentSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buildComponentAndInject();
        if(hasCurrentSession) {
            Intent intent = new Intent(this, TweetActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            LoginViewBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
            binding.setViewModel(new LoginViewModel(this, binding));
        }
    }

    private void buildComponentAndInject() {
        DaggerLoginComponent.builder()
                .twitterComponent(TwitterApp.get(this).component())
                .build()
                .inject(this);
    }
}

