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
    suspend fun getCategories() : CategoriesModel

    @GET("/api/v2.1/collections")
    suspend fun getCollections(@Query("lat") lat: String,
                       @Query("lon") lon: String) : CollectionModel

    @GET("/api/v2.1/cities")
    suspend fun getCity(@Query("lat") lat: String,
                @Query("lon") lon: String) : CityModel

    @GET("/api/v2.1/cuisines")
    suspend fun getCuisines(@Query("lat") lat: String,
                    @Query("lon") lon: String) : CuisineModel

    @GET("/api/v2.1/establishments")
    suspend fun getEstablishments(@Query("lat") lat: String,
                          @Query("lon") lon: String) : EstablishmentModel

    @GET("/api/v2.1/search")
    suspend fun searchByCollectionId(@Query("lat") lat: String,
                             @Query("lon") lon: String,
                             @Query("collection_id") collectionId: Int) : SearchModel

    @GET("/api/v2.1/search")
    suspend fun searchByCategoryId(@Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("category") category: Int) : SearchModel

    @GET("/api/v2.1/search")
    suspend fun searchByCuisineId(@Query("lat") lat: String,
                           @Query("lon") lon: String,
                           @Query("cuisines") cuisines: String) : SearchModel

    @GET("/api/v2.1/search")
    suspend fun search(@Query("lat") lat: String,
               @Query("lon") lon: String,
               @Query("q") query: String) : SearchModel

    @GET("/api/v2.1/restaurant")
    suspend fun getRestaurantDetails(@Query("res_id") resId: Int) : RestaurantInfo

    @GET("/api/v2.1/reviews")
    suspend fun getReviews(@Query("res_id") resId: Int) : ReviewModel
}