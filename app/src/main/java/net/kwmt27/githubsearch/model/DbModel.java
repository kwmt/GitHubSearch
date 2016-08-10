package net.kwmt27.githubsearch.model;

import android.content.Context;

import net.kwmt27.githubsearch.entity.TokenEntity;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class DbModel {


    public DbModel(Context context) {

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(context).build();
        Realm.setDefaultConfiguration(realmConfiguration);

    }

    public void saveToken(final TokenEntity entity) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(entity);
            }
        });
    }

    public TokenEntity getToken() {
        Realm realm = Realm.getDefaultInstance();
        final TokenEntity tokenEntity = realm.where(TokenEntity.class).findFirst();
        realm.close();
        return tokenEntity;
    }

}
