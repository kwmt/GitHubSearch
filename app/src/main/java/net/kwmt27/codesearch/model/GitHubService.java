package net.kwmt27.codesearch.model;

import net.kwmt27.codesearch.entity.GithubRepoEntity;
import net.kwmt27.codesearch.entity.SearchCodeResultEntity;
import net.kwmt27.codesearch.entity.SearchRepositoryResultEntity;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GitHubService {
    @GET("user/repos")
    Observable<Response<List<GithubRepoEntity>>> fetchUserRepository(@Query(value = "page") Integer page, @Query(value = "sort") SortType type);

    @GET("repos/{owner}/{repo}")
    Observable<GithubRepoEntity> fetchRepo(@Path("owner") String user, @Path("repo") String repo);

    @Headers("Accept: application/vnd.github.v3.text-match+json")
    @GET("search/code")
    Observable<Response<SearchCodeResultEntity>> searchCode(@Query(value = "q", encoded = true) String keyword, @Query(value = "page") Integer page);

    @Headers("Accept: application/vnd.github.v3.text-match+json")
    @GET("search/repositories")
    Observable<Response<SearchRepositoryResultEntity>> searchRepositories(@Query(value = "q", encoded = true) String keyword, @Query(value = "page") Integer page);
}
