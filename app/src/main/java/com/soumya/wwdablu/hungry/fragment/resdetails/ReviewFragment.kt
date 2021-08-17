package com.soumya.wwdablu.hungry.fragment.resdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.adapter.ReviewAdapter
import com.soumya.wwdablu.hungry.databinding.FragResReviewsBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.network.model.reviews.ReviewModel
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.repository.HungryRepo
import com.soumya.wwdablu.hungry.viewmodel.RestaurantDetailsViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class ReviewFragment : HungryFragment<FragResReviewsBinding>() {

    private lateinit var mReviewModel: ReviewModel
    private lateinit var mAdapter: ReviewAdapter

    private lateinit var mViewModel: RestaurantDetailsViewModel
    private lateinit var mRestaurant: RestaurantInfo

    companion object {

        fun newInstance() : ReviewFragment {

            return ReviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResReviewsBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(RestaurantDetailsViewModel::class.java)

        mRestaurant = mViewModel.getRestaurantInfo()!!
        mViewBinding.resInfo = mRestaurant

        mViewBinding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
        getReviews(mRestaurant.r.resId)

        return mViewBinding.root
    }

    private fun getReviews(resId: Int) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
        }

        CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
            mReviewModel = HungryRepo.getReviews(resId)
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch {
                if(this@ReviewFragment::mReviewModel.isInitialized) {

                    mViewBinding.lotRecommendedLoading.cancelAnimation()
                    mViewBinding.lotRecommendedLoading.visibility = View.GONE
                    mViewBinding.rvReviews.visibility = View.VISIBLE

                    mAdapter = ReviewAdapter(mReviewModel)
                    mViewBinding.rvReviews.adapter = mAdapter
                    mViewBinding.review = mReviewModel
                }
            }
        }
    }
}