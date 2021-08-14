package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.databinding.ActivityCollectionDetailsBinding
import com.soumya.wwdablu.hungry.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.adapter.GenericSearchResultAdapter
import com.soumya.wwdablu.hungry.network.model.collections.CollectionInfo
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class CollectionDetailsActivity : HungryActivity() {

    private lateinit var mViewBinding: ActivityCollectionDetailsBinding
    private lateinit var mSearchModel: SearchModel
    private lateinit var mAdapter: GenericSearchResultAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityCollectionDetailsBinding.inflate(layoutInflater)

        mViewBinding.rvResByCollection.layoutManager = LinearLayoutManager(this)
        getRestaurantsByCollectionId(intent.getStringExtra("collection_id") ?: "1")

        val collectionInfoParcelable: Parcelable? = intent.getParcelableExtra("collection_info")
        if(collectionInfoParcelable != null) {
            val model: CollectionInfo = collectionInfoParcelable as CollectionInfo
            mViewBinding.collectionModel = model
            loadImageIntoImageView(model.imageUrl, mViewBinding.ivRestaurantImage)
        }

        setContentView(mViewBinding.root)
    }

    private fun getRestaurantsByCollectionId(collectionId: String) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            finish()
        }

        ioScope.launch(exceptionHandler) {
            mSearchModel = HungryRepo.searchByCollectionId(collectionId.toInt())
        }.invokeOnCompletion {

            mainScope.launch(defaultExceptionHandler) {
                if(this@CollectionDetailsActivity::mSearchModel.isInitialized &&
                    !this@CollectionDetailsActivity::mAdapter.isInitialized) {
                    mAdapter = GenericSearchResultAdapter(mSearchModel, mListener)
                    mViewBinding.rvResByCollection.adapter = mAdapter

                    mViewBinding.lotLoading.cancelAnimation()
                    mViewBinding.lotLoading.visibility = View.GONE
                    mViewBinding.rvResByCollection.visibility = View.VISIBLE
                }
            }
        }
    }

    private val mListener = object: RestaurantItemSelector {
        override fun onRestaurantClicked(restaurant: RestaurantInfo) {
            mainScope.launch {
                val intent: Intent = Intent(this@CollectionDetailsActivity, RestaurantDetailsActivity::class.java)
                intent.putExtra("res_details", restaurant)
                startActivity(intent)
            }
        }
    }
}