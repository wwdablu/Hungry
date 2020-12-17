package com.soumya.wwdablu.hungry.repository

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
import com.soumya.wwdablu.hungry.network.DataProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

internal object HungryRepo {

    private lateinit var mCategoriesModel: CategoriesModel
    private lateinit var mCollectionModel: CollectionModel
    private lateinit var mCityModel: CityModel
    private lateinit var mCuisineModel: CuisineModel
    private lateinit var mEstablishmentModel: EstablishmentModel

    fun getCity() : CityModel {
        return mCityModel
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

    fun getCity(lat: String, lon: String) : Observable<List<City>> {

        return Observable.create {

            if(this::mCityModel.isInitialized && mCityModel.model.isEmpty()) {
                it.onNext(mCityModel.model)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCity(lat, lon)
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

    fun getCollections(lat: String, lon: String) : Observable<List<CuratedCollection>> {

        return Observable.create {

            if(this::mCollectionModel.isInitialized && mCollectionModel.list.isEmpty()) {
                it.onNext(mCollectionModel.list)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCollections(lat, lon)
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

    fun getCollections(cityId: String) : Observable<List<CuratedCollection>> {

        return Observable.create {

            if(this::mCollectionModel.isInitialized && mCollectionModel.list.isEmpty()) {
                it.onNext(mCollectionModel.list)
                it.onComplete()
                return@create
            }

            DataProvider.call().getCollections(cityId)
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

    fun getCuisine(cityId: String) : Observable<List<Cuisine>> {

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

            DataProvider.call().getCuisines(cityId)
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

    fun getEstablishments(cityId: String) : Observable<List<Establishment>> {

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

            DataProvider.call().getEstablishments(cityId)
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
}