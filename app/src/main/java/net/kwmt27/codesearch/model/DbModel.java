package net.kwmt27.codesearch.model;

import android.content.Context;

import net.kwmt27.codesearch.entity.TokenEntity;

import io.realm.Realm;
import io.realm.RealmConfiguration;


/**
 * 現在未使用。TODO: 検索履歴の保存に使っていきたい
 */
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
        return tokenEntity;
    }

}
