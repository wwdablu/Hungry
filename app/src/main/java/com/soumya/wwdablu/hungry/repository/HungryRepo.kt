package com.soumya.wwdablu.hungry.repository

import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.network.model.categories.Categories
import com.soumya.wwdablu.hungry.network.model.categories.CategoriesModel
import com.soumya.wwdablu.hungry.network.model.cities.City
import com.soumya.wwdablu.hungry.network.model.cities.CityModel
import com.soumya.wwdablu.hungry.network.model.collections.CollectionModel
import com.soumya.wwdablu.hungry.network.model.collections.CuratedCollection
import com.soumya.wwdablu.hungry.network.model.cuisine.Cuisine
import com.soumya.wwdablu.hungry.network.model.cuisine.CuisineModel
import com.soumya.wwdablu.hungry.network.model.establishments.Establishment
import com.soumya.wwdablu.hungry.network.model.establishments.EstablishmentModel
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.network.DataProvider
import com.soumya.wwdablu.hungry.network.model.reviews.ReviewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

internal object HungryRepo {

    private lateinit var mLocation: Pair<String, String>

    private lateinit var mCategoriesModel: CategoriesModel
    private lateinit var mCollectionModel: CollectionModel
    private lateinit var mCityModel: CityModel
    private lateinit var mCuisineModel: CuisineModel
    private lateinit var mEstablishmentModel: EstablishmentModel

    fun getCityModel() : CityModel {
        return mCityModel
    }

    fun setLocation(lat: String, lon: String) {
        mLocation = Pair(lat, lon)
    }

    fun getLocation(): Pair<String, String> {
        return mLocation
    }

    suspend fun getCategories() : List<Categories> {

        if(this::mCategoriesModel.isInitialized && mCategoriesModel.categories.isNotEmpty()) {
            return mCategoriesModel.categories
        }

        return withContext(Dispatchers.IO) {
            mCategoriesModel = DataProvider.call().getCategories()
            mCategoriesModel.categories
        }
    }

    suspend fun getCity() : List<City> {

        if(this::mCityModel.isInitialized && mCityModel.model.isNotEmpty()) {
            return mCityModel.model
        }

        mCityModel = DataProvider.call().getCity(mLocation.first, mLocation.second)
        return mCityModel.model
    }

    suspend fun getCollections() : List<CuratedCollection> {

        if(this::mCollectionModel.isInitialized && mCollectionModel.list.isNotEmpty()) {
            return mCollectionModel.list
        }

        return withContext(Dispatchers.IO) {
            mCollectionModel = DataProvider.call().getCollections(mLocation.first, mLocation.second)
            mCollectionModel.list
        }
    }

    suspend fun getCuisine() : List<Cuisine> {

        if(this::mCuisineModel.isInitialized && mCuisineModel.list.isNotEmpty()) {
            val listCuisine: LinkedList<Cuisine> = LinkedList()
            mCuisineModel.list.forEach { me ->
                listCuisine.add(me.cuisine)
            }
            return listCuisine
        }

        return withContext(Dispatchers.IO) {
            mCuisineModel = DataProvider.call().getCuisines(mLocation.first, mLocation.second)
            val listCuisine: LinkedList<Cuisine> = LinkedList()
            mCuisineModel.list.forEach { me ->
                listCuisine.add(me.cuisine)
            }
            listCuisine
        }
    }

    suspend fun getEstablishments() : List<Establishment> {

        if(this::mEstablishmentModel.isInitialized && mEstablishmentModel.list.isNotEmpty()) {
            val listEstablishment: LinkedList<Establishment> = LinkedList()
            mEstablishmentModel.list.forEach { me ->
                listEstablishment.add(me.establishment)
            }
            return listEstablishment
        }

        return withContext(Dispatchers.IO) {
            mEstablishmentModel = DataProvider.call().getEstablishments(mLocation.first, mLocation.second)
            val listEstablishment: LinkedList<Establishment> = LinkedList()
            mEstablishmentModel.list.forEach { me ->
                listEstablishment.add(me.establishment)
            }
            listEstablishment
        }
    }

    suspend fun getRestaurantDetails(resId: Int) : RestaurantInfo {

        return withContext(Dispatchers.IO) {
            DataProvider.call().getRestaurantDetails(resId)
        }
    }

    suspend fun getReviews(resId: Int) : ReviewModel {

        return withContext(Dispatchers.IO) {
            DataProvider.call().getReviews(resId)
        }
    }

    suspend fun searchByCollectionId(collectionId: Int) : SearchModel {

        return withContext(Dispatchers.IO) {
            DataProvider.call().searchByCollectionId(mLocation.first, mLocation.second, collectionId)
        }
    }

    suspend fun searchByCategoryId(category: CategoryEnum) : SearchModel {

        return withContext(Dispatchers.IO) {
            DataProvider.call().searchByCategoryId(mLocation.first, mLocation.second, category.ordinal)
        }
    }

    suspend fun searchByCuisineId(cuisineId: String) : SearchModel {

        return withContext(Dispatchers.IO) {
            DataProvider.call().searchByCuisineId(mLocation.first, mLocation.second, cuisineId)
        }
    }

    suspend fun search(query: String) : SearchModel {

        return withContext(Dispatchers.IO) {
            DataProvider.call().search(mLocation.first, mLocation.second, query)
        }
    }
}