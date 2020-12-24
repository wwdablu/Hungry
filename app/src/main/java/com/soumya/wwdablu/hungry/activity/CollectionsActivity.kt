package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.CollectionDetailsActivity
import com.soumya.wwdablu.hungry.databinding.ActivityCollectionsBinding
import com.soumya.wwdablu.hungry.fragment.CollectionItemSelector
import com.soumya.wwdablu.hungry.model.network.collections.CollectionInfo
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class CollectionsActivity : AppCompatActivity(), CollectionItemSelector {

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

        HungryRepo.getCollections()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<CuratedCollection>>() {
                override fun onNext(t: List<CuratedCollection>?) {

                    if(t != null) {
                        mAdapter = CollectionsAdapter(t, this@CollectionsActivity)
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                    finish()
                }

                override fun onComplete() {

                    if(this@CollectionsActivity::mAdapter.isInitialized) {
                        mViewBinding.rvCollections.adapter = mAdapter
                    }
                }
            })
    }

    override fun onCollectionClicked(collection: CollectionInfo) {
        runOnUiThread {
            val intent: Intent = Intent(this, CollectionDetailsActivity::class.java)
            intent.putExtra("collection_id", collection.id)
            intent.putExtra("collection_info", collection)
            startActivity(intent)
        }
    }
}