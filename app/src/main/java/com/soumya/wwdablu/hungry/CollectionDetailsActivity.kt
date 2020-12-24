package com.soumya.wwdablu.hungry

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.activity.RestaurantDetailsActivity
import com.soumya.wwdablu.hungry.databinding.ActivityCollectionDetailsBinding
import com.soumya.wwdablu.hungry.fragment.RestaurantItemSelector
import com.soumya.wwdablu.hungry.fragment.generic.GenericSearchModelAdapter
import com.soumya.wwdablu.hungry.model.network.collections.CollectionInfo
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class CollectionDetailsActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityCollectionDetailsBinding
    private lateinit var mSearchModel: SearchModel
    private lateinit var mAdapter: GenericSearchModelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityCollectionDetailsBinding.inflate(layoutInflater)

        mViewBinding.rvResByCollection.layoutManager = LinearLayoutManager(this)
        getRestaurantsByCollectionId(intent.getStringExtra("collection_id") ?: "1")

        val collectionInfoParcelable: Parcelable? = intent.getParcelableExtra("collection_info")
        if(collectionInfoParcelable != null) {
            val model: CollectionInfo = collectionInfoParcelable as CollectionInfo
            mViewBinding.collectionModel = model
            Glide.with(mViewBinding.root.context)
                .load(model.imageUrl)
                .placeholder(R.drawable.default_card_bg)
                .into(mViewBinding.ivRestaurantImage)
        }

        setContentView(mViewBinding.root)
    }

    private fun getRestaurantsByCollectionId(collectionId: String) {

        HungryRepo.searchByCollectionId(collectionId.toInt())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<SearchModel>() {
                override fun onNext(t: SearchModel?) {

                    if(t != null) {
                        mSearchModel = t
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                    finish()
                }

                override fun onComplete() {

                    if(this@CollectionDetailsActivity::mSearchModel.isInitialized &&
                            !this@CollectionDetailsActivity::mAdapter.isInitialized) {
                        mAdapter = GenericSearchModelAdapter(mSearchModel, mListener)
                        mViewBinding.rvResByCollection.adapter = mAdapter
                    }
                }
            })
    }

    private val mListener = object: RestaurantItemSelector {
        override fun onRestaurantClicked(restaurant: RestaurantInfo) {
            runOnUiThread {
                val intent: Intent = Intent(this@CollectionDetailsActivity, RestaurantDetailsActivity::class.java)
                intent.putExtra("resid", restaurant.id)
                startActivity(intent)
            }
        }
    }
}