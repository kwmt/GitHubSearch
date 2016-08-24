package net.kwmt27.codesearch.dagger;

import net.kwmt27.codesearch.BuildConfig;
import net.kwmt27.codesearch.model.ApiClient;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApiClientModule {

    @Provides
    @Singleton
    public ApiClient provideApiClient(){
        return new ApiClient(BuildConfig.BASE_API_URL);
    }
}
