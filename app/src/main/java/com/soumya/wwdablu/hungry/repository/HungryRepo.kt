package com.soumya.wwdablu.hungry.repository

import com.soumya.wwdablu.hungry.model.network.Categories
import com.soumya.wwdablu.hungry.model.network.CategoriesModel
import com.soumya.wwdablu.hungry.network.DataProvider
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

internal object HungryRepo {

    private lateinit var mCategoriesModel: CategoriesModel

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
}