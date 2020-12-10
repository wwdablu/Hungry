package com.soumya.wwdablu.zomatobuddy.dagger

//import com.soumya.wwdablu.zomatobuddy.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule(private val baseUrl: String?) {
//    @Singleton
//    @Provides
//    fun provideZomatoServiceApi(): ZomatoServiceApi {
//        return provideRetrofit().create<ZomatoServiceApi?>(ZomatoServiceApi::class.java)
//    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(provideOkHttpClient())
                .addConverterFactory(provideGsonConverterFactory())
                .addCallAdapterFactory(provideRxCallAdapterFactory())
                .build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideScalarConverterFactory(): ScalarsConverterFactory {
        return ScalarsConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRxCallAdapterFactory(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .addInterceptor(provideInterceptor())
                .addInterceptor(provideHttpLogginInterceptor())
                .build()
    }

    @Singleton
    @Provides
    fun provideInterceptor(): Interceptor? {
        return Interceptor { chain: Interceptor.Chain? ->
            val modifiedRequest = chain?.request()?.newBuilder()
                    //.addHeader("user-key", BuildConfig.ZOMATO_API_KEY)
                    ?.build()
            chain?.proceed(modifiedRequest)
        }
    }

    @Singleton
    @Provides
    fun provideHttpLogginInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return loggingInterceptor
    }
}