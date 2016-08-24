package net.kwmt27.codesearch.dagger;

import net.kwmt27.codesearch.GithubServiceTest;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = MockApiClientModule.class)
public interface TestComponent {
    void inject(GithubServiceTest test);
}
