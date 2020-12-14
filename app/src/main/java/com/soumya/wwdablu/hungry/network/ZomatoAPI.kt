package com.soumya.wwdablu.hungry.network

import com.soumya.wwdablu.hungry.model.network.CategoriesModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface ZomatoAPI {

    @GET("/api/v2.1/categories")
    fun getCategories() : Observable<CategoriesModel>
}