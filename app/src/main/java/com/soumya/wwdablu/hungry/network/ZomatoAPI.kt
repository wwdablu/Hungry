package com.soumya.wwdablu.hungry.network

import com.soumya.wwdablu.hungry.model.network.categories.CategoriesModel
import com.soumya.wwdablu.hungry.model.network.collections.CollectionModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ZomatoAPI {

    @GET("/api/v2.1/categories")
    fun getCategories() : Observable<CategoriesModel>

    @GET("/api/v2.1/collections")
    fun getCollections(@Query("lat") lat: String, @Query("lon") lon: String) : Observable<CollectionModel>
}