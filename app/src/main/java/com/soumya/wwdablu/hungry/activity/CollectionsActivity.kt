package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.activity.common.HungryActivity
import com.soumya.wwdablu.hungry.adapter.CollectionsAdapter
import com.soumya.wwdablu.hungry.databinding.ActivityCollectionsBinding
import com.soumya.wwdablu.hungry.iface.CollectionItemSelector
import com.soumya.wwdablu.hungry.network.model.collections.CollectionInfo
import com.soumya.wwdablu.hungry.network.model.collections.CuratedCollection
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class CollectionsActivity : HungryActivity(), CollectionItemSelector {

    private lateinit var mAdapter: CollectionsAdapter
    private lateinit var mViewBinding: ActivityCollectionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityCollectionsBinding.inflate(layoutInflater)

        mViewBinding.rvCollections.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        getCollection()

        setContentView(mViewBinding.root)
    }

    private fun getCollection() {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            finish()
        }

        ioScope.launch(exceptionHandler) {
            val t: List<CuratedCollection> = HungryRepo.getCollections()
            mAdapter = CollectionsAdapter(t, this@CollectionsActivity)
        }.invokeOnCompletion {
            mainScope.launch {
                if(this@CollectionsActivity::mAdapter.isInitialized) {
                    mViewBinding.lotLoading.cancelAnimation()
                    mViewBinding.lotLoading.visibility = View.GONE
                    mViewBinding.rvCollections.visibility = View.VISIBLE
                    mViewBinding.rvCollections.adapter = mAdapter
                }
            }
        }
    }

    override fun onCollectionClicked(collection: CollectionInfo) {
        mainScope.launch {
            val intent: Intent = Intent(this@CollectionsActivity, CollectionDetailsActivity::class.java)
            intent.putExtra("collection_id", collection.id)
            intent.putExtra("collection_info", collection)
            startActivity(intent)
        }
    }
}