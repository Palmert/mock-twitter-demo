package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.DataModule;
import com.thompalmer.mocktwitterdemo.data.api.ApiModule;
import com.thompalmer.mocktwitterdemo.domain.DomainModule;

import dagger.Component;

@ApplicationScope
@Component(modules = {TwitterAppModule.class, ApiModule.class, DataModule.class, DomainModule.class})
public interface TwitterComponent extends TwitterGraph {

    final class Initializer {
        private Initializer() {
        }

        static TwitterComponent init(TwitterApplication app) {
            return DaggerTwitterComponent.builder()
                    .twitterAppModule(new TwitterAppModule(app))
                    .build();
        }
    }
}
