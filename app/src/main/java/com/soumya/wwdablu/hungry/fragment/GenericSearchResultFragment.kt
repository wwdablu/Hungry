package com.soumya.wwdablu.hungry.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.activity.RestaurantDetailsActivity
import com.soumya.wwdablu.hungry.activity.SearchActivity
import com.soumya.wwdablu.hungry.adapter.GenericSearchResultAdapter
import com.soumya.wwdablu.hungry.databinding.FragSearchResultGenericBinding
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.defines.SearchBy
import com.soumya.wwdablu.hungry.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class GenericSearchResultFragment private constructor() : HungryFragment<FragSearchResultGenericBinding>(),
        RestaurantItemSelector {

    private lateinit var mGenericSearchResultAdapter: GenericSearchResultAdapter
    private lateinit var mSearchModel: SearchModel

    private lateinit var mPrimarySearchCriteria: Pair<SearchBy, String>
    private lateinit var mFallbackSearchCriteria: Pair<SearchBy, String>

    companion object {

        fun newInstance(primarySearch: SearchBy, primarySearchParam: String,
                        fallbackSearch: SearchBy = SearchBy.None, fallbackSearchParam: String = "")
        : GenericSearchResultFragment {

            val fragment = GenericSearchResultFragment()

            fragment.mPrimarySearchCriteria = Pair(primarySearch, primarySearchParam)
            fragment.mFallbackSearchCriteria = Pair(fallbackSearch, fallbackSearchParam)

            return fragment
        }
    }

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragSearchResultGenericBinding.inflate(inflater, container, false)

        mViewBinding.rvCatList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        if(this::mSearchModel.isInitialized) {
            mViewBinding.rvCatList.adapter = mGenericSearchResultAdapter
        }

        mViewBinding.searchBar.btnSearch.setOnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        }

        getDataBySearchMode()

        mViewBinding.city = HungryRepo.getCityModel().model[0]

        return mViewBinding.root
    }

    private fun getDataBySearchMode() {

        when (mPrimarySearchCriteria.first) {

            SearchBy.Category -> {
                getByCategory(CategoryEnum.valueOf(mPrimarySearchCriteria.second))
            }

            SearchBy.Collection -> {
                if(mPrimarySearchCriteria.second.isNotEmptyAndNotBlank()) {
                    getByCollectionId(mPrimarySearchCriteria.second.toInt())
                }
            }

            SearchBy.Cuisine -> {
                if(mPrimarySearchCriteria.second.isNotEmptyAndNotBlank()) {
                    getByCuisineId(mPrimarySearchCriteria.second)
                }
            }

            SearchBy.Query -> {
                if(mPrimarySearchCriteria.second.isNotEmptyAndNotBlank()) {
                    getByQuery(mPrimarySearchCriteria.second)
                }
            }

            SearchBy.None -> {
                Timber.e("Primary search mode is given as None")
            }
        }
    }

    private fun getByCategory(categoryEnum: CategoryEnum) {

        HungryRepo.searchByCategoryId(categoryEnum)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: GenericObserver() {
                override fun onNext(t: SearchModel?) {
                    if(t != null) {
                        mSearchModel = t
                        mGenericSearchResultAdapter = GenericSearchResultAdapter(t, this@GenericSearchResultFragment)
                    } else {
                        getByCollectionId(if(mFallbackSearchCriteria.second.isEmptyOrBlank()) 1 else
                            mFallbackSearchCriteria.second.toInt())
                    }
                }
            })
    }

    private fun getByCollectionId(collectionId: Int) {

        HungryRepo.searchByCollectionId(collectionId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(GenericObserver())
    }

    private fun getByCuisineId(cuisineId: String) {

        HungryRepo.searchByCuisineId(cuisineId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(GenericObserver())
    }

    private fun getByQuery(query: String) {

        HungryRepo.search(query)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(GenericObserver())
    }

    private open inner class GenericObserver : DisposableObserver<SearchModel>() {
        override fun onNext(t: SearchModel?) {
            if(t != null) {
                mSearchModel = t
                mGenericSearchResultAdapter = GenericSearchResultAdapter(t, this@GenericSearchResultFragment)
            }
        }

        override fun onError(e: Throwable?) {
            Timber.e(e)
        }

        override fun onComplete() {
            if(this@GenericSearchResultFragment::mGenericSearchResultAdapter.isInitialized) {
                mViewBinding.rvCatList.adapter = mGenericSearchResultAdapter
            }
        }
    }

    override fun onRestaurantClicked(restaurant: RestaurantInfo) {

        activity?.runOnUiThread {
            val intent: Intent = Intent(context, RestaurantDetailsActivity::class.java)
            intent.putExtra("res_details", restaurant)
            startActivity(intent)
        }
    }
}