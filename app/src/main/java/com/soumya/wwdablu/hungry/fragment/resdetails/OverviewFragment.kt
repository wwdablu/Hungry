package com.soumya.wwdablu.hungry.fragment.resdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.ViewModelProvider
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.FragResOverviewBinding
import com.soumya.wwdablu.hungry.fragment.HungryFragment
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import com.soumya.wwdablu.hungry.viewmodel.RestaurantDetailsViewModel

class OverviewFragment : HungryFragment<FragResOverviewBinding>() {

    private lateinit var mRestaurant: RestaurantInfo
    private lateinit var mViewModel: RestaurantDetailsViewModel

    companion object {

        fun newInstance(): OverviewFragment {
            return OverviewFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResOverviewBinding.inflate(inflater, container, false)
        mViewModel = ViewModelProvider(requireActivity()).get(RestaurantDetailsViewModel::class.java)

        mRestaurant = mViewModel.getRestaurantInfo()!!
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