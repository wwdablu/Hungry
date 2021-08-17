package com.soumya.wwdablu.hungry.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
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
import com.soumya.wwdablu.hungry.viewmodel.GenericSearchResultViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class GenericSearchResultFragment : HungryFragment<FragSearchResultGenericBinding>(),
        RestaurantItemSelector {

    private lateinit var mGenericSearchResultAdapter: GenericSearchResultAdapter
    private lateinit var mSearchModel: SearchModel
    private lateinit var mViewModel: GenericSearchResultViewModel

    private lateinit var mPrimarySearchCriteria: Pair<SearchBy, String>
    private lateinit var mFallbackSearchCriteria: Pair<SearchBy, String>

    companion object {

        fun newInstance(primarySearch: SearchBy, primarySearchParam: String,
                        fallbackSearch: SearchBy = SearchBy.None, fallbackSearchParam: String = "")
        : GenericSearchResultFragment {

            val fragment = GenericSearchResultFragment()

            val bundle = Bundle()
            bundle.putInt("primarySearch", primarySearch.ordinal)
            bundle.putSerializable("primarySearchParam", primarySearchParam)
            bundle.putInt("fallbackSearch", fallbackSearch.ordinal)
            bundle.putSerializable("fallbackSearchParam", fallbackSearchParam)

            fragment.arguments = bundle

            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val bundle = arguments

        mPrimarySearchCriteria = Pair(SearchBy.values()[bundle?.getInt("primarySearch") ?: 0], bundle?.getString("primarySearchParam") ?: "")
        mFallbackSearchCriteria = Pair(SearchBy.values()[bundle?.getInt("fallbackSearch") ?: 0], bundle?.getString("fallbackSearchParam") ?: "")

        mViewModel = ViewModelProvider(requireActivity()).get(GenericSearchResultViewModel::class.java)

        mViewBinding = FragSearchResultGenericBinding.inflate(inflater, container, false)

        val spanCount = if (isScreenInPortrait()) 1 else 2
        mViewBinding.rvCatList.layoutManager = GridLayoutManager(context, spanCount)
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

        mViewModel.getByCategory(categoryEnum).observe(this, {
            mSearchModel = it
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
            onQueryCompletion()
        })
    }

    private fun getByCollectionId(collectionId: Int) {

        mViewModel.getByCollectionId(collectionId).observe(this, {
            mSearchModel = it
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
            onQueryCompletion()
        })
    }

    private fun getByCuisineId(cuisineId: String) {

        mViewModel.getByCuisineId(cuisineId).observe(this, {
            mSearchModel = it
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
            onQueryCompletion()
        })
    }

    private fun getByQuery(query: String) {

        mViewModel.getByQuery(query).observe(this, {
            mSearchModel = it
            mGenericSearchResultAdapter = GenericSearchResultAdapter(mSearchModel, this@GenericSearchResultFragment)
            onQueryCompletion()
        })
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

        val intent = Intent(context, RestaurantDetailsActivity::class.java)
        intent.putExtra("res_details", restaurant)
        startActivity(intent)
    }
}