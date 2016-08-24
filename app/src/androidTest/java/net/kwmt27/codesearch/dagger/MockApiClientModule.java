package net.kwmt27.codesearch.dagger;

import net.kwmt27.codesearch.model.ApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MockApiClientModule {

    @Provides
    @Singleton
    public ApiClient provideApiClient(){
        // urlはMockServerのURLを使用する
        return new ApiClient("");
    }
}
