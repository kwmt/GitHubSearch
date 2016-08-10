package net.kwmt27.githubsearch.model;

import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface GitHubLoginService {

    @GET("/login/oauth/access_token")
    Observable<Response<ResponseBody>> fetchAccessToken(@Query(value = "code") String code, @Query(value = "client_id") String clientId, @Query(value = "client_secret") String clientSecret);
}
