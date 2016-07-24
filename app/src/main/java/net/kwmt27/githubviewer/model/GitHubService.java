package net.kwmt27.githubviewer.model;

import net.kwmt27.githubviewer.entity.GithubRepo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface GitHubService {
    @GET("users/{user}/repos")
    Observable<List<GithubRepo>> listRepos(@Path("user") String user);
}
