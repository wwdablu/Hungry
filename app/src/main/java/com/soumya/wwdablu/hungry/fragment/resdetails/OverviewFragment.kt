package com.soumya.wwdablu.hungry.fragment.resdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.soumya.wwdablu.hungry.databinding.FragResOverviewBinding
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo

class OverviewFragment(restaurant: RestaurantInfo) : Fragment() {

    private val mRestaurant: RestaurantInfo = restaurant
    private lateinit var mViewBinding: FragResOverviewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragResOverviewBinding.inflate(inflater, container, false)

        mViewBinding.resInfo = mRestaurant

        return mViewBinding.root
    }
}