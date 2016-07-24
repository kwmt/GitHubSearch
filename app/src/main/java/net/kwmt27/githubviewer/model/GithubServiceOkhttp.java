package net.kwmt27.githubviewer.model;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;

import net.kwmt27.githubviewer.ModelLocator;
import net.kwmt27.githubviewer.entity.GithubRepo;
import net.kwmt27.githubviewer.entity.GithubResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class GithubServiceOkhttp {

    private static final String TAG = GithubServiceOkhttp.class.getSimpleName();



    private Gson gson = new GsonBuilder()
            .create();

    public Subscription fetchGithub(final Subscriber<GithubResponse> subscriber) {
        final String path = "/";
        return Observable.fromCallable(
                new Callable<GithubResponse>() {
                    @Override
                    public GithubResponse call() throws Exception {
                        ApiClient apiClient = ModelLocator.getApiClient();
                        Response response = apiClient.request(path, null);
                        return parseGithubResponse(response);
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }
    public Subscription fetchUserListRepos(final Subscriber<List<GithubRepo>> subscriber) {
        return ModelLocator.getApiClient().api.listRepos("kwmt")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);


    }


    private GithubResponse parseGithubResponse(Response response) throws JsonParseException, IOException, JSONException {
        String json = "";
        try {
            json = response.body().string();
            JSONObject jsonObject = new JSONObject(json);
            Log.d(TAG, jsonObject.toString(4));
            return gson.fromJson(jsonObject.toString(), GithubResponse.class);
        } catch (IOException e) {
            throw new IOException(e);
        } catch (JsonParseException e) {
            throw new JsonParseException(e.getMessage());
        } catch (JSONException e) {
            throw new JSONException(e.getMessage());
        }
    }
}
