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
import com.soumya.wwdablu.hungry.databinding.FragDiningBinding
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class DiningFragment : Fragment() {

    private lateinit var mViewBinding: FragDiningBinding
    private lateinit var mCollectionAdapter: CuratedCollectionsAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = DataBindingUtil.inflate(inflater, R.layout.frag_dining, container, false)
        mViewBinding.city = HungryRepo.getCityModel().model[0]

        mViewBinding.rvCuratedCollection.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        getCollection()

        mViewBinding.tvCollectionSeeall.setOnClickListener {
            startActivity(Intent(context, CollectionsActivity::class.java))
        }

        return mViewBinding.root
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
                    //
                }

                override fun onComplete() {
                    mViewBinding.rvCuratedCollection.adapter = mCollectionAdapter
                }
            })
    }
}