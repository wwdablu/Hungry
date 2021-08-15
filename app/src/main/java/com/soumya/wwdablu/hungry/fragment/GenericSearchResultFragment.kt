package com.soumya.wwdablu.hungry.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class GenericSearchResultFragment : HungryFragment<FragSearchResultGenericBinding>(),
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
        mViewBinding.rvCatList.addOnItemTouchListener(mItemTouchListener)

        if(this::mSearchModel.isInitialized) {
            mViewBinding.rvCatList.adapter = mGenericSearchResultAdapter
        }

        mViewBinding.searchBar.rlSearchContainer.setOnClickListener {
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

    private val mExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.e(throwable)
    }

    private fun getByCategory(categoryEnum: CategoryEnum) {

        CoroutineScope(Dispatchers.IO).launch(mExceptionHandler) {
            mSearchModel = HungryRepo.searchByCategoryId(categoryEnum)
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
//            getByCollectionId(if(mFallbackSearchCriteria.second.isEmptyOrBlank()) 1 else
//                mFallbackSearchCriteria.second.toInt())
        }.invokeOnCompletion {
            onQueryCompletion()
        }
    }

    private fun getByCollectionId(collectionId: Int) {

        CoroutineScope(Dispatchers.IO).launch(mExceptionHandler) {
            mSearchModel = HungryRepo.searchByCollectionId(collectionId)
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
        }.invokeOnCompletion {
            onQueryCompletion()
        }
    }

    private fun getByCuisineId(cuisineId: String) {

        CoroutineScope(Dispatchers.IO).launch(mExceptionHandler) {
            mSearchModel = HungryRepo.searchByCuisineId(cuisineId)
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
        }.invokeOnCompletion {
            onQueryCompletion()
        }
    }

    private fun getByQuery(query: String) {

        CoroutineScope(Dispatchers.IO).launch(mExceptionHandler) {
            mSearchModel = HungryRepo.search(query)
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
        }.invokeOnCompletion {
            onQueryCompletion()
        }
    }

    private fun onQueryCompletion() {
        CoroutineScope(Dispatchers.Main).launch {
            if(this@GenericSearchResultFragment::mGenericSearchResultAdapter.isInitialized) {
                mViewBinding.lotLoading.cancelAnimation()
                mViewBinding.lotLoading.visibility = View.GONE
                mViewBinding.rvCatList.visibility = View.VISIBLE
                mViewBinding.rvCatList.adapter = mGenericSearchResultAdapter
            }
        }
    }

    private val mItemTouchListener: RecyclerView.OnItemTouchListener = object: RecyclerView.OnItemTouchListener {
        override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {

            val view: View = rv.findChildViewUnder(e.x, e.y) ?: return false
            val viewHolder: RecyclerView.ViewHolder = rv.findContainingViewHolder(view) ?: return false
            mGenericSearchResultAdapter.handleTouchEvent(e, viewHolder)

            return false
        }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
            //
        }

        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
            //
        }
    }

    override fun onRestaurantClicked(restaurant: RestaurantInfo) {

        CoroutineScope(Dispatchers.Main).launch {
            val intent: Intent = Intent(context, RestaurantDetailsActivity::class.java)
            intent.putExtra("res_details", restaurant)
            startActivity(intent)
        }
    }
}