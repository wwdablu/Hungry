package com.soumya.wwdablu.hungry.network

import com.soumya.wwdablu.hungry.network.model.categories.CategoriesModel
import com.soumya.wwdablu.hungry.network.model.cities.CityModel
import com.soumya.wwdablu.hungry.network.model.collections.CollectionModel
import com.soumya.wwdablu.hungry.network.model.cuisine.CuisineModel
import com.soumya.wwdablu.hungry.network.model.establishments.EstablishmentModel
import com.soumya.wwdablu.hungry.network.model.reviews.ReviewModel
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ZomatoAPI {

    @GET("/api/v2.1/categories")
    fun getCategories() : Observable<CategoriesModel>

    @GET("/api/v2.1/collections")
    fun getCollections(@Query("lat") lat: String,
                       @Query("lon") lon: String) : Observable<CollectionModel>

    @GET("/api/v2.1/cities")
    fun getCity(@Query("lat") lat: String,
                @Query("lon") lon: String) : Observable<CityModel>

    @GET("/api/v2.1/cuisines")
    fun getCuisines(@Query("lat") lat: String,
                    @Query("lon") lon: String) : Observable<CuisineModel>

    @GET("/api/v2.1/establishments")
    fun getEstablishments(@Query("lat") lat: String,
                          @Query("lon") lon: String) : Observable<EstablishmentModel>

    @GET("/api/v2.1/search")
    fun searchByCollectionId(@Query("lat") lat: String,
                             @Query("lon") lon: String,
                             @Query("collection_id") collectionId: Int) : Observable<SearchModel>

    @GET("/api/v2.1/search")
    fun searchByCategoryId(@Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("category") category: Int) : Observable<SearchModel>

    @GET("/api/v2.1/search")
    fun searchByCuisineId(@Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("cuisines") cuisines: String) : Observable<SearchModel>

    @GET("/api/v2.1/search")
    fun search(@Query("lat") lat: String,
               @Query("lon") lon: String,
               @Query("q") query: String) : Observable<SearchModel>

    @GET("/api/v2.1/restaurant")
    fun getRestaurantDetails(@Query("res_id") resId: Int) : Observable<RestaurantInfo>

    @GET("/api/v2.1/reviews")
    fun getReviews(@Query("res_id") resId: Int) : Observable<ReviewModel>
}