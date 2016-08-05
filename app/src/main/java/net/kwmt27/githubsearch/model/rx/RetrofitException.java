package net.kwmt27.githubsearch.model.rx;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RetrofitException extends RuntimeException {
    public static RetrofitException httpError(String url, Response response, Throwable throwable, Retrofit retrofit) {
        String message = response.code() + " " + response.message();
        return new RetrofitException(message, url, response, Kind.HTTP, throwable, retrofit);
    }

    public static RetrofitException networkError(IOException exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.NETWORK, exception, null);
    }

    public static RetrofitException unexpectedError(Throwable exception) {
        return new RetrofitException(exception.getMessage(), null, null, Kind.UNEXPECTED, exception, null);
    }

    public enum Kind {
        NETWORK,
        HTTP,
        UNEXPECTED
    }

    private final String mUrl;
    private final Response mResponse;
    private final Kind mKind;
    private final Retrofit mRetrofit;

    RetrofitException(String message, String url, Response response, Kind kind, Throwable exception, Retrofit retrofit) {
        super(message, exception);
        mUrl = url;
        mResponse = response;
        mKind = kind;
        mRetrofit = retrofit;
    }

    public String getUrl() {
        return mUrl;
    }

    public Response getResponse() {
        return mResponse;
    }

    public Kind getKind() {
        return mKind;
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public <T> T getErrorBodyAs(Class<T> type) throws IOException {
        if (mResponse == null || mResponse.errorBody() == null) {
            return null;
        }
        Converter<ResponseBody, T> converter = mRetrofit.responseBodyConverter(type, new Annotation[0]);
        return converter.convert(mResponse.errorBody());
    }
}
