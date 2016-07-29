package net.kwmt27.githubviewer.model;

import net.kwmt27.githubviewer.BuildConfig;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_API_URL = "https://api.github.com"; // BuildConfig.BASE_API_URL;
    public final GitHubService api;

    private OkHttpClient mClient;

    public ApiClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mClient = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.create()))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        api = retrofit.create(GitHubService.class);

    }


    public String buildUrl(String path) {
        return BASE_API_URL + path;
    }

    /**
     * リクエスト共通処理
     *
     * @param path エンドポイント
     * @param body POST内容
     */
    public Response request(final String path, final RequestBody body) throws IOException {

        String url = buildUrl(path);
        Request.Builder builder = new Request.Builder()
                .url(url);

        if (body != null) {
            builder = builder.post(body);
        }
        final Request request = builder.build();
        return mClient.newCall(request).execute();
    }

}
