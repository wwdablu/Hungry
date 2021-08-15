package com.soumya.wwdablu.hungry.fragment.resdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soumya.wwdablu.hungry.adapter.PhotosAdapter
import com.soumya.wwdablu.hungry.databinding.FragResPhotoBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class PhotosFragment : HungryFragment<FragResPhotoBinding>() {

    private lateinit var mAdapter: PhotosAdapter
    private lateinit var mPhotoUrls: List<String>

    companion object {

        fun newInstance(restaurant: RestaurantInfo) : PhotosFragment {

            val fragment = PhotosFragment()
            fragment.getPhotos(restaurant)

            return fragment
        }
    }

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResPhotoBinding.inflate(inflater, container, false)
        mViewBinding.rvPhotos.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        return mViewBinding.root
    }

    private fun getPhotos(restaurant: RestaurantInfo) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
        }

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            mPhotoUrls = RestaurantInfoUtil.getPhotos(restaurant)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch(exceptionHandler) {
                if(this@PhotosFragment::mPhotoUrls.isInitialized) {
                    mAdapter = PhotosAdapter(mPhotoUrls)
                    mViewBinding.rvPhotos.adapter = mAdapter
                }
            }
        }
    }
}