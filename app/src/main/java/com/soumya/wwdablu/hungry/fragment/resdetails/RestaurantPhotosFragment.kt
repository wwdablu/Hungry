package com.soumya.wwdablu.hungry.fragment.resdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soumya.wwdablu.hungry.adapter.PhotosAdapter
import com.soumya.wwdablu.hungry.databinding.FragResPhotoBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class RestaurantPhotosFragment(restaurant: RestaurantInfo) : HungryFragment<FragResPhotoBinding>() {

    private lateinit var mAdapter: PhotosAdapter
    private lateinit var mPhotoUrls: List<String>

    init {
        getPhotos(restaurant)
    }

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResPhotoBinding.inflate(inflater, container, false)
        mViewBinding.rvPhotos.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        return mViewBinding.root
    }

    private fun getPhotos(restaurant: RestaurantInfo) {

        RestaurantInfoUtil.getPhotos(restaurant)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<String>>() {
                override fun onNext(t: List<String>?) {

                    if(t == null) {
                        return
                    }

                    mPhotoUrls = t
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {

                    if(this@RestaurantPhotosFragment::mPhotoUrls.isInitialized) {
                        mAdapter = PhotosAdapter(mPhotoUrls)
                        mViewBinding.rvPhotos.adapter = mAdapter
                    }
                }
            })
    }
}