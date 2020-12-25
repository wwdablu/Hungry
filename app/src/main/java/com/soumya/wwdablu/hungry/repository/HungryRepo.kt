package com.soumya.wwdablu.hungry.repository

import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.model.network.categories.Categories
import com.soumya.wwdablu.hungry.model.network.categories.CategoriesModel
import com.soumya.wwdablu.hungry.model.network.cities.City
import com.soumya.wwdablu.hungry.model.network.cities.CityModel
import com.soumya.wwdablu.hungry.model.network.collections.CollectionModel
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.model.network.cuisine.Cuisine
import com.soumya.wwdablu.hungry.model.network.cuisine.CuisineModel
import com.soumya.wwdablu.hungry.model.network.establishments.Establishment
import com.soumya.wwdablu.hungry.model.network.establishments.EstablishmentModel
import com.soumya.wwdablu.hungry.model.network.search.Restaurant
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.network.DataProvider
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
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

    fun getCategories() : Observable<List<Categories>> {

        return Observable.create {

            if(this::mCategoriesModel.isInitialized && mCategoriesModel.categories.isNotEmpty()) {
                it.onNext(mCategoriesModel.categories)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCategories()
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<CategoriesModel>() {
                    override fun onNext(t: CategoriesModel?) {
                        mCategoriesModel = t ?: CategoriesModel(LinkedList())
                        it.onNext(LinkedList(mCategoriesModel.categories))
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }

    fun getCity() : Observable<List<City>> {

        return Observable.create {

            if(this::mCityModel.isInitialized && mCityModel.model.isEmpty()) {
                it.onNext(mCityModel.model)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCity(mLocation.first, mLocation.second)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<CityModel>() {
                    override fun onNext(t: CityModel?) {
                        mCityModel = t ?: CityModel(LinkedList(), "success")
                        it.onNext(LinkedList(mCityModel.model))
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }

    fun getCollections() : Observable<List<CuratedCollection>> {

        return Observable.create {

            if(this::mCollectionModel.isInitialized && mCollectionModel.list.isEmpty()) {
                it.onNext(mCollectionModel.list)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCollections(mLocation.first, mLocation.second)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<CollectionModel>() {
                    override fun onNext(t: CollectionModel?) {
                        mCollectionModel = t ?: CollectionModel(LinkedList())
                        it.onNext(LinkedList(mCollectionModel.list))
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }

    fun getCuisine() : Observable<List<Cuisine>> {

        return Observable.create {

            if(this::mCuisineModel.isInitialized && mCuisineModel.list.isEmpty()) {
                val listCuisine: LinkedList<Cuisine> = LinkedList()
                mCuisineModel.list.forEach { me ->
                    listCuisine.add(me.cuisine)
                }
                it.onNext(listCuisine)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCuisines(mLocation.first, mLocation.second)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<CuisineModel>() {
                    override fun onNext(t: CuisineModel?) {
                        mCuisineModel = t ?: CuisineModel(LinkedList())
                        val listCuisine: LinkedList<Cuisine> = LinkedList()
                        mCuisineModel.list.forEach { me ->
                            listCuisine.add(me.cuisine)
                        }
                        it.onNext(listCuisine)
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }

    fun getEstablishments() : Observable<List<Establishment>> {

        return Observable.create {

            if(this::mEstablishmentModel.isInitialized && mEstablishmentModel.list.isEmpty()) {
                val listEstablishment: LinkedList<Establishment> = LinkedList()
                mEstablishmentModel.list.forEach { me ->
                    listEstablishment.add(me.establishment)
                }
                it.onNext(listEstablishment)
                it.onComplete()
                return@create
            }

            DataProvider.call().getEstablishments(mLocation.first, mLocation.second)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<EstablishmentModel>() {
                    override fun onNext(t: EstablishmentModel?) {
                        mEstablishmentModel = t ?: EstablishmentModel(LinkedList())
                        val listEstablishment: LinkedList<Establishment> = LinkedList()
                        mEstablishmentModel.list.forEach { me ->
                            listEstablishment.add(me.establishment)
                        }
                        it.onNext(listEstablishment)
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }

    fun getRestaurantDetails(resId: Int) : Observable<RestaurantInfo> {

        return Observable.create {

            DataProvider.call().getRestaurantDetails(resId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : DisposableObserver<RestaurantInfo>() {
                    override fun onNext(t: RestaurantInfo?) {
                        it.onNext(t)
                    }

                    override fun onError(e: Throwable?) {
                        it.onError(e)
                    }

                    override fun onComplete() {
                        it.onComplete()
                    }
                })
        }
    }

    fun searchByCollectionId(collectionId: Int) : Observable<SearchModel?> {

        return Observable.create {

            handleSearchObservable(DataProvider.call().searchByCollectionId(
                mLocation.first, mLocation.second, collectionId), it)
        }
    }

    fun searchByCategoryId(category: CategoryEnum) : Observable<SearchModel?> {

        return Observable.create {

            handleSearchObservable(DataProvider.call().searchByCategoryId(
                mLocation.first, mLocation.second, category.ordinal), it)
        }
    }

    fun searchByCuisineId(cuisineId: String) : Observable<SearchModel?> {

        return Observable.create {

            handleSearchObservable(DataProvider.call().searchByCuisineId(
                    mLocation.first, mLocation.second, cuisineId), it)
        }
    }

    fun search(query: String) : Observable<SearchModel?> {

        return Observable.create {

            handleSearchObservable(DataProvider.call().search(
                    mLocation.first, mLocation.second, query), it)
        }
    }

    private fun handleSearchObservable(observable: Observable<SearchModel>,
                                       emitter: ObservableEmitter<SearchModel?>) {

        observable.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<SearchModel>() {
                override fun onNext(t: SearchModel?) {
                    emitter.onNext(t)
                }

                override fun onError(e: Throwable?) {
                    emitter.onError(e)
                }

                override fun onComplete() {
                    emitter.onComplete()
                }
            })
    }
}