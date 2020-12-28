package com.soumya.wwdablu.hungry.fragment.resdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.adapter.ReviewAdapter
import com.soumya.wwdablu.hungry.databinding.FragResReviewsBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.network.model.reviews.ReviewModel
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber

class ReviewFragment private constructor() : HungryFragment<FragResReviewsBinding>() {

    private lateinit var mReviewModel: ReviewModel
    private lateinit var mAdapter: ReviewAdapter

    private lateinit var mRestaurant: RestaurantInfo

    companion object {

        fun newInstance(restaurant: RestaurantInfo) : ReviewFragment {

            val fragment = ReviewFragment()
            fragment.mRestaurant = restaurant

            return fragment
        }
    }

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResReviewsBinding.inflate(inflater, container, false)
        mViewBinding.resInfo = mRestaurant

        mViewBinding.rvReviews.layoutManager = LinearLayoutManager(requireContext())
        getReviews(mRestaurant.r.resId)

        return mViewBinding.root
    }

    private fun getReviews(resId: Int) {

        HungryRepo.getReviews(resId)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<ReviewModel>() {
                override fun onNext(t: ReviewModel?) {

                    if(t != null) {
                        mReviewModel = t
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {

                    if(this@ReviewFragment::mReviewModel.isInitialized) {

                        mViewBinding.lotRecommendedLoading.cancelAnimation()
                        mViewBinding.lotRecommendedLoading.visibility = View.GONE
                        mViewBinding.rvReviews.visibility = View.VISIBLE

                        mAdapter = ReviewAdapter(mReviewModel)
                        mViewBinding.rvReviews.adapter = mAdapter
                        mViewBinding.review = mReviewModel
                    }
                }
            })
    }
}