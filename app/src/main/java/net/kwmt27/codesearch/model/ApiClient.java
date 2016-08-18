package net.kwmt27.codesearch.model;

import android.text.TextUtils;

import net.kwmt27.codesearch.BuildConfig;
import net.kwmt27.codesearch.ModelLocator;
import net.kwmt27.codesearch.model.rx.GsonFactory;
import net.kwmt27.codesearch.model.rx.RxErrorHandlingCallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static final String BASE_API_URL = BuildConfig.BASE_API_URL;
    public final GitHubService api;
    public final GitHubLoginService login;
    public final Retrofit mRetrofit;

    private OkHttpClient mClient;

    public ApiClient() {

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        mClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new HeaderInterceptor())
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();


        mRetrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_API_URL)
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.create()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();

        api = mRetrofit.create(GitHubService.class);

        Retrofit retrofitForLogin = new Retrofit.Builder()
                .baseUrl("https://github.com")
                .client(mClient)
                .addConverterFactory(GsonConverterFactory.create(GsonFactory.create()))
                .addCallAdapterFactory(RxErrorHandlingCallAdapterFactory.create())
                .build();
        login = retrofitForLogin.create(GitHubLoginService.class);
    }



    private static class HeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request.Builder builder = original.newBuilder();
            if(!TextUtils.isEmpty(ModelLocator.getLoginModel().getAccessToken())) {
                builder.header("Authorization", "token " + ModelLocator.getLoginModel().getAccessToken());
            }
            return chain.proceed(builder.build());
        }
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
