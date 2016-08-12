package net.kwmt27.githubsearch.model;

import net.kwmt27.githubsearch.entity.TokenEntity;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;
import rx.Observable;

public interface GitHubLoginService {

    @Headers("Accept: application/json")
    @GET("/login/oauth/access_token")
    Observable<TokenEntity> fetchAccessToken(@Query(value = "code") String code, @Query(value = "client_id") String clientId, @Query(value = "client_secret") String clientSecret);
}
