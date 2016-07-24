package net.kwmt27.rxjavasample.model;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ApiClient {

    private static final String BASE_API_URL = "https://api.github.com"; // BuildConfig.BASE_API_URL;

    private OkHttpClient mClient;

    public ApiClient() {

        mClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
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
