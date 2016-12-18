package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.api.ApiModule;
import com.thompalmer.mocktwitterdemo.domain.UseCaseModule;

import dagger.Component;

@ApplicationScope
@Component(modules = {TwitterAppModule.class, ApiModule.class, UseCaseModule.class})
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
