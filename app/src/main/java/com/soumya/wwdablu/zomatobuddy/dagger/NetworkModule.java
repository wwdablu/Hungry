package com.soumya.wwdablu.zomatobuddy.dagger;

import com.soumya.wwdablu.zomatobuddy.BuildConfig;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    private String baseUrl;

    public NetworkModule(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Singleton
    @Provides
    public ZomatoServiceApi provideZomatoServiceApi() {

        return provideRetrofit().create(ZomatoServiceApi.class);
    }

    @Singleton
    @Provides
    public Retrofit provideRetrofit() {

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient())
                .addConverterFactory(provideGsonConverterFactory())
                .addCallAdapterFactory(provideRxCallAdapterFactory())
                .build();
    }

    @Singleton
    @Provides
    public GsonConverterFactory provideGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @Singleton
    @Provides
    public RxJava2CallAdapterFactory provideRxCallAdapterFactory() {
        return RxJava2CallAdapterFactory.create();
    }

    @Singleton
    @Provides
    public OkHttpClient provideOkHttpClient() {

        return new OkHttpClient.Builder()
                .addInterceptor(provideInterceptor())
                .build();
    }

    @Singleton
    @Provides
    public Interceptor provideInterceptor() {

        return chain -> {

            Request modifiedRequest = chain.request().newBuilder()
                    .addHeader("user-key", BuildConfig.ZOMATO_API_KEY)
                    .build();

            return chain.proceed(modifiedRequest);
        };
    }
}
