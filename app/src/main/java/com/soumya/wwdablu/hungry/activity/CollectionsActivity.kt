package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ActivityCollectionsBinding
import com.soumya.wwdablu.hungry.fragment.CuratedCollectionsAdapter
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class CollectionsActivity : AppCompatActivity() {

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
                    mAdapter = CollectionsAdapter(t!!)
                }

                override fun onError(e: Throwable?) {
                    //
                }

                override fun onComplete() {
                    mViewBinding.rvCollections.adapter = mAdapter
                }
            })
    }
}