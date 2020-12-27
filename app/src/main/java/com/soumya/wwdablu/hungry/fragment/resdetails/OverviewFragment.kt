package com.soumya.wwdablu.hungry.fragment.resdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.FragResOverviewBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil

class OverviewFragment private constructor() : HungryFragment<FragResOverviewBinding>() {

    private lateinit var mRestaurant: RestaurantInfo

    companion object {

        fun newInstance(restaurant: RestaurantInfo) : OverviewFragment {

            val fragment = OverviewFragment()
            fragment.mRestaurant = restaurant

            return fragment
        }
    }

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResOverviewBinding.inflate(inflater, container, false)
        mViewBinding.resInfo = mRestaurant

        populateOtherInfo(mViewBinding.root.context)

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