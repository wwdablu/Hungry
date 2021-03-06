package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.adapter.SearchAdapter
import com.soumya.wwdablu.hungry.databinding.ActivitySearchBinding
import com.soumya.wwdablu.hungry.defines.SearchBy
import com.soumya.wwdablu.hungry.iface.CuisineItemSelector
import com.soumya.wwdablu.hungry.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.network.model.cuisine.Cuisine
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class SearchActivity : HungryActivity(), RestaurantItemSelector, CuisineItemSelector {

    private lateinit var mViewBinding: ActivitySearchBinding
    private lateinit var mAdapter: SearchAdapter

    private var mCuisineList: LinkedList<Cuisine> = LinkedList()
    private var mSearchModel: SearchModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivitySearchBinding.inflate(layoutInflater)

        mViewBinding.rvSearchResults.layoutManager = LinearLayoutManager(this)

        mViewBinding.etSearch.setOnEditorActionListener(mActionListener)
        mViewBinding.etSearch.doAfterTextChanged {
            postRunnableOnMain(mSearchRunnable, 500, true)
        }
        hideKeyboard()

        setContentView(mViewBinding.root)
    }

    private fun search(content: String) {

        if(content.isBlank() || content.isEmpty()) {
            mCuisineList.clear()
            mSearchModel = null
            updateSearchAdapter()
            return
        }

        HungryRepo.getCuisine().flatMap {
            mCuisineList.clear()
            mCuisineList.addAll(it.filter { me ->
                me.cuisineName.contains(content)
            })
            HungryRepo.search(content)
        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<SearchModel?>() {
                override fun onNext(t: SearchModel?) {
                    mSearchModel = t
                }

                override fun onError(e: Throwable?) {
                    //
                }

                override fun onComplete() {
                    updateSearchAdapter()
                }
            })
    }

    private fun updateSearchAdapter() {
        if(!this@SearchActivity::mAdapter.isInitialized) {
            mAdapter = SearchAdapter(mCuisineList, mSearchModel ?:
            SearchModel(0, 0, 0, LinkedList()),
                    this@SearchActivity, this@SearchActivity)

            mViewBinding.rvSearchResults.adapter = mAdapter
        } else {
            mAdapter.setSearchResults(mCuisineList, mSearchModel)
        }
    }

    private val mSearchRunnable: Runnable = Runnable {
        search(mViewBinding.etSearch.text.toString())
    }

    private val mActionListener: TextView.OnEditorActionListener = object: TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

            hideKeyboard()

            val content: String = mViewBinding.etSearch.text.toString()
            if(content.isEmpty() || content.isBlank()) {
                return true
            }

            search(content)
            return true
        }
    }

    override fun onRestaurantClicked(restaurant: RestaurantInfo) {
        runOnUiThread {
            val intent: Intent = Intent(this@SearchActivity, RestaurantDetailsActivity::class.java)
            intent.putExtra("res_details", restaurant)
            startActivity(intent)
        }
    }

    override fun onCuisineClicked(cuisine: Cuisine) {
        runOnUiThread {
            val intent: Intent = GenericSearchResultActivity.createLaunchIntent(this@SearchActivity,
                    SearchBy.Cuisine, cuisine.cuisineId)
            startActivity(intent)
        }
    }
}