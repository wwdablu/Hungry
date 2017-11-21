package com.soumya.wwdablu.zomatobuddy.network;

import com.soumya.wwdablu.zomatobuddy.model.categories.CategoryResponse;
import com.soumya.wwdablu.zomatobuddy.model.restaurant.RestaurantResponse;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ZomatoServiceApi {

    @GET("/api/v2.1/categories")
    Observable<CategoryResponse> getCategories();

    @GET("/api/v2.1/search")
    Observable<SearchResponse> getCategorySearch(@Query("lat") String latitude,
                                                 @Query("lon") String longitude,
                                                 @Query("category") String category);

    @GET("/api/v2.1/restaurant")
    Observable<RestaurantResponse> getRestaurantDetails(@Query("res_id") String restaurantId);
}
