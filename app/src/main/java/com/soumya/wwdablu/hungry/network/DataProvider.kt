package com.soumya.wwdablu.hungry.network

import com.soumya.wwdablu.hungry.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object DataProvider {

    private val mZomatoAPI: ZomatoAPI

    init {
        mZomatoAPI = Retrofit.Builder()
                .baseUrl(BuildConfig.ZOMATO_BASE_URL)
                .client(getHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(ZomatoAPI::class.java)
    }

    fun call() : ZomatoAPI = mZomatoAPI

    private fun getHttpClient() : OkHttpClient {

        return OkHttpClient.Builder()
                .addInterceptor {
                    val request = it.request().newBuilder()
                            .addHeader("user-key", BuildConfig.ZOMATO_API_KEY)
                            .build()

                    it.proceed(request)
                }
                .build()
    }
}