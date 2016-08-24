package net.kwmt27.codesearch.dagger;

import net.kwmt27.codesearch.model.BaseModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApiClientModule.class)
public interface AppComponent {
    void inject(BaseModel model);
}
