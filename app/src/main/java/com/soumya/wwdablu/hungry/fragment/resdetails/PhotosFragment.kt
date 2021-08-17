package com.soumya.wwdablu.hungry.fragment.resdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.soumya.wwdablu.hungry.adapter.PhotosAdapter
import com.soumya.wwdablu.hungry.databinding.FragResPhotoBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import com.soumya.wwdablu.hungry.viewmodel.RestaurantDetailsViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class PhotosFragment : HungryFragment<FragResPhotoBinding>() {

    private lateinit var mAdapter: PhotosAdapter
    private lateinit var mPhotoUrls: List<String>

    private lateinit var mViewModel: RestaurantDetailsViewModel

    companion object {

        fun newInstance() : PhotosFragment {

            return PhotosFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResPhotoBinding.inflate(inflater, container, false)
        mViewBinding.rvPhotos.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)

        mViewModel = ViewModelProvider(requireActivity()).get(RestaurantDetailsViewModel::class.java)
        getPhotos(mViewModel.getRestaurantInfo()!!)

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