package com.soumya.wwdablu.hungry.repository

import com.soumya.wwdablu.hungry.model.network.categories.Categories
import com.soumya.wwdablu.hungry.model.network.categories.CategoriesModel
import com.soumya.wwdablu.hungry.model.network.collections.CollectionModel
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.network.DataProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

internal object HungryRepo {

    private lateinit var mCategoriesModel: CategoriesModel
    private lateinit var mCollectionModel: CollectionModel

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
}