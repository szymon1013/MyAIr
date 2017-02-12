package com.szymon1013.myair;

import android.app.Application;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.szymon1013.myair.network.AirDataDownloader;

import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetModule {
    public static final String PARAM_NAME_TOKEN = "token";
    private String mBaseUrl;
    private String mToken;

    public NetModule(String baseUrl, String token) {
        this.mBaseUrl = baseUrl;
        this.mToken = token;
    }

    @Provides
    @Singleton
    Cache provideOkHttpCache(Application application) {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(application.getCacheDir(), cacheSize);
        return cache;
    }


    @Provides
    @Singleton
    StethoInterceptor provideStethoInterceptor() {
        return new StethoInterceptor();
    }

    @Provides
    @Singleton
    Interceptor provideAuthenticationInterceptor() {
        return new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                HttpUrl originalHttpUrl = original.url();

                HttpUrl url = originalHttpUrl.newBuilder()
                        .addQueryParameter(PARAM_NAME_TOKEN, mToken)
                        .build();
                Request.Builder requestBuilder = original.newBuilder()
                        .url(url);

                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        };
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(Cache cache, StethoInterceptor interceptor, Interceptor authenticationInterceptor) {
        return new OkHttpClient.Builder().
                addNetworkInterceptor(interceptor).
                addInterceptor(authenticationInterceptor).
                cache(cache).build();
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(mBaseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public AirDataDownloader provideAirDataDownloader(Retrofit retrofit) {
        return new AirDataDownloader(retrofit);
    }

}
