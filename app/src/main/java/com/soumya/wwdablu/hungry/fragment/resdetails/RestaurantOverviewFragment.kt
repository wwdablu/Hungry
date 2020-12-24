package com.soumya.wwdablu.hungry.fragment.resdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.FragResOverviewBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class RestaurantOverviewFragment(restaurant: RestaurantInfo) : HungryFragment<FragResOverviewBinding>() {

    private val mRestaurant: RestaurantInfo = restaurant

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResOverviewBinding.inflate(inflater, container, false)
        populateOtherInfo(mViewBinding.root.context)

        mViewBinding.resInfo = mRestaurant
        RestaurantInfoUtil.loadFeatureImage(mViewBinding.ivRestaurantImage, mRestaurant)

        return mViewBinding.root
    }

    private fun populateOtherInfo(context: Context) {

        mRestaurant.highlights.forEach {
            val param: LinearLayout.LayoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            val textView: AppCompatTextView = AppCompatTextView(context)
            textView.layoutParams = param
            textView.text = it
            textView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_check, 0, 0, 0);
            mViewBinding.llHighlightsContainer.addView(textView)
        }
    }
}