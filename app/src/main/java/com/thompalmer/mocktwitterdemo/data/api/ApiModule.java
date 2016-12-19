package com.thompalmer.mocktwitterdemo.data.api;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.db.common.DatabaseInteractorImpl;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

import static com.thompalmer.mocktwitterdemo.data.db.server.TwitterServerDatabase.*;

@Module
public class ApiModule {
    @Provides
    @ApplicationScope
    OkHttpClient provideOkHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Provides
    @ApplicationScope
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https:///www.palmer.tech")
                .client(okHttpClient)
                .build();
    }

    @Provides
    @ApplicationScope
    NetworkBehavior networkBehavior() {
        return NetworkBehavior.create();
    }

    @Provides
    @ApplicationScope
    MockRetrofit provideMockRetrofit(Retrofit retrofit, NetworkBehavior networkBehavior) {
        return new MockRetrofit.Builder(retrofit)
                .networkBehavior(networkBehavior)
                .build();
    }

    @Provides
    @ApplicationScope
    TwitterService provideMockTwitterService(MockRetrofit mockRetrofit, @Named(TWITTER_SERVER_DB) DatabaseInteractorImpl db, RepositoryInteractor<Tweet> tweetRepository) {
        BehaviorDelegate<TwitterService> twitter = mockRetrofit.create(TwitterService.class);
        return new LocalTwitterServer(twitter, db, tweetRepository);
    }
}