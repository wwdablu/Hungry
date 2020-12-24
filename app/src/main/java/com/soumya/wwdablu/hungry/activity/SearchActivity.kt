package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.adapter.SearchAdapter
import com.soumya.wwdablu.hungry.databinding.ActivitySearchBinding
import com.soumya.wwdablu.hungry.fragment.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.model.network.cuisine.Cuisine
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*

class SearchActivity : HungryActivity(), RestaurantItemSelector {

    private lateinit var mViewBinding: ActivitySearchBinding
    private lateinit var mAdapter: SearchAdapter

    private var mCuisineList: LinkedList<Cuisine> = LinkedList()
    private var mSearchModel: SearchModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivitySearchBinding.inflate(layoutInflater)

        mViewBinding.rvSearchResults.layoutManager = LinearLayoutManager(this)

        mViewBinding.etSearch.setOnEditorActionListener(mActionListener)

        setContentView(mViewBinding.root)
    }

    private fun search(content: String) {

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

                    if(!this@SearchActivity::mAdapter.isInitialized) {
                        mAdapter = SearchAdapter(mCuisineList, mSearchModel ?:
                            SearchModel(0, 0, 0, LinkedList()),
                                this@SearchActivity)

                        mViewBinding.rvSearchResults.adapter = mAdapter
                    }
                }
            })
    }

    private val mActionListener: TextView.OnEditorActionListener = object: TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {

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
}