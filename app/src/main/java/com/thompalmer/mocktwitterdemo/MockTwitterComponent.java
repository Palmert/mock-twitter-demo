package com.thompalmer.mocktwitterdemo;

import com.thompalmer.mocktwitterdemo.data.ApiModule;
import com.thompalmer.mocktwitterdemo.domain.UseCaseModule;

import dagger.Component;

@ApplicationScope
@Component(modules = {MockTwitterAppModule.class, ApiModule.class, UseCaseModule.class})
public interface MockTwitterComponent extends MockTwitterGraph {

    final class Initializer {
        private Initializer() {
        }

        static MockTwitterComponent init(MockTwitterApp app) {
            return DaggerMockTwitterComponent.builder()
                    .mockTwitterAppModule(new MockTwitterAppModule(app))
                    .build();
        }
    }
}
