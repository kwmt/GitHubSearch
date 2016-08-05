package net.kwmt27.githubsearch.model.rx;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {

    private final RxJavaCallAdapterFactory mOriginal;

    private RxErrorHandlingCallAdapterFactory() {
        mOriginal = RxJavaCallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, mOriginal.get(returnType, annotations, retrofit));
    }


    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit mRetrofit;
        private final CallAdapter<?> mWrapped;

        public RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> wrapped) {
            mRetrofit = retrofit;
            mWrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return mWrapped.responseType();
        }

        @Override
        public <R> Observable<?> adapt(Call<R> call) {
            return ((Observable) mWrapped.adapt(call))
                    .onErrorResumeNext(new Func1<Throwable, Observable>() {
                        @Override
                        public Observable call(Throwable throwable) {
                            return Observable.error(asRetrofitException(throwable));
                        }
                    });
        }

        private RetrofitException asRetrofitException(Throwable throwable) {
            if (throwable instanceof HttpException) {
                Response response = ((HttpException) throwable).response();
                return RetrofitException.httpError(response.raw().request().url().toString(), response, throwable, mRetrofit);
            }
            if (throwable instanceof IOException) {
                return RetrofitException.networkError((IOException) throwable);
            }
            return RetrofitException.unexpectedError(throwable);
        }

    }


}
