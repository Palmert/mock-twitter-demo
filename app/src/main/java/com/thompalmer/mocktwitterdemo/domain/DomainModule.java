package com.thompalmer.mocktwitterdemo.domain;

import com.thompalmer.mocktwitterdemo.ApplicationScope;
import com.thompalmer.mocktwitterdemo.data.api.TwitterService;
import com.thompalmer.mocktwitterdemo.data.api.model.entity.Tweet;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.SharePreferenceWrapper;
import com.thompalmer.mocktwitterdemo.data.sharedpreference.UserSessionStorage;
import com.thompalmer.mocktwitterdemo.domain.interactor.RepositoryInteractor;
import com.thompalmer.mocktwitterdemo.domain.interactor.UserSessionInteractor;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class DomainModule {

    @Provides
    @ApplicationScope
    UserSessionInteractor providesUserSessionPersister(UserSessionStorage userSessionStorage) {
        return userSessionStorage;
    }

    @Provides
    @ApplicationScope
    AttemptUserLogin provideAttemptUserLogin(TwitterService twitterService, UserSessionInteractor sessionPersister) {
        return new AttemptUserLogin(twitterService, sessionPersister);
    }

    @Provides
    @ApplicationScope
    PerformLogout providePerformLogout(UserSessionInteractor sessionPersister, RepositoryInteractor<Tweet> tweetRepository,
                                            @Named("last_created_at") SharePreferenceWrapper<String> lastCreatedAt) {
        return new PerformLogout(sessionPersister, tweetRepository, lastCreatedAt);
    }

    @Provides
    @ApplicationScope
    ListTweets provideListTweets(TwitterService twitterService, RepositoryInteractor<Tweet> tweetRepository,
                                 UserSessionInteractor sessionPersister, @Named("last_created_at") SharePreferenceWrapper<String> lastCreatedAt) {
        return new ListTweets(twitterService, tweetRepository, sessionPersister, lastCreatedAt);
    }

    @Provides
    @ApplicationScope
    CreateTweet providesCreateTweet(TwitterService twitterService, UserSessionInteractor sessionPersister, RepositoryInteractor<Tweet> tweetRepository) {
        return new CreateTweet(twitterService, sessionPersister, tweetRepository);
    }

    @Provides
    @ApplicationScope
    GetLatestTweet providesGetLatestTweet(RepositoryInteractor<Tweet> tweetRepository) {
        return new GetLatestTweet(tweetRepository);
    }
}
