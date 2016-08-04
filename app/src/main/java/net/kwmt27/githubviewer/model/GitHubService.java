package net.kwmt27.githubviewer.model;

import net.kwmt27.githubviewer.entity.GithubRepoEntity;
import net.kwmt27.githubviewer.entity.SearchCodeResultEntity;
import net.kwmt27.githubviewer.entity.SearchRepositoryResultEntity;

import java.util.List;

import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<Response<List<GithubRepoEntity>>> listRepos(@Path("user") String user, @Query(value = "page") Integer page);

    @GET("repos/{owner}/{repo}")
    Observable<GithubRepoEntity> fetchRepo(@Path("owner") String user, @Path("repo") String repo);

    @Headers("Accept: application/vnd.github.v3.text-match+json")
    @GET("search/code")
    Observable<SearchCodeResultEntity> searchCode(@Query(value = "q", encoded = true) String keyword);

    @Headers("Accept: application/vnd.github.v3.text-match+json")
    @GET("search/repositories")
    Observable<SearchRepositoryResultEntity> searchRepositories(@Query(value = "q", encoded = true) String keyword);
}
