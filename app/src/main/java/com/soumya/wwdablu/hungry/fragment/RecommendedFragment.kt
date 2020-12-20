package com.soumya.wwdablu.hungry.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.activity.CollectionsActivity
import com.soumya.wwdablu.hungry.activity.RestaurantDetailsActivity
import com.soumya.wwdablu.hungry.databinding.FragRecommendedBinding
import com.soumya.wwdablu.hungry.fragment.generic.GenericSearchModelAdapter
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RecommendedFragment : Fragment(), RestaurantItemSelector {

    private lateinit var mViewBinding: FragRecommendedBinding
    private lateinit var mCollectionAdapter: CuratedCollectionsAdapter
    private lateinit var mGenericSearchModelAdapter: GenericSearchModelAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = DataBindingUtil.inflate(inflater, R.layout.frag_recommended, container, false)
        mViewBinding.city = HungryRepo.getCityModel().model[0]

        mViewBinding.rvCuratedCollection.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getCollection()

        mViewBinding.rvRecommendedForYou.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        getRecommendation()

        mViewBinding.tvCollectionSeeall.setOnClickListener {
            startActivity(Intent(context, CollectionsActivity::class.java))
        }

        return mViewBinding.root
    }

    private fun getRecommendation() {

        HungryRepo.searchByCollectionId(1)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<SearchModel>() {
                override fun onNext(t: SearchModel?) {

                    if(t == null) {
                        return
                    }

                    mGenericSearchModelAdapter = GenericSearchModelAdapter(t, this@RecommendedFragment)
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {
                    mViewBinding.rvRecommendedForYou.adapter = mGenericSearchModelAdapter
                }
            })
    }

    private fun getCollection() {

        HungryRepo.getCollections()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<CuratedCollection>>() {
                override fun onNext(t: List<CuratedCollection>?) {

                    mCollectionAdapter = CuratedCollectionsAdapter(t!!)
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {
                    mViewBinding.rvCuratedCollection.adapter = mCollectionAdapter
                }
            })
    }

    override fun onRestaurantClicked(restaurant: RestaurantInfo) {

        activity?.runOnUiThread {
            val intent: Intent = Intent(context, RestaurantDetailsActivity::class.java)
            intent.putExtra("resid", restaurant.id)
            startActivity(intent)
        }
    }
}