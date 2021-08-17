package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationBarView
import com.soumya.wwdablu.hungry.customview.RestaurantDetailsBottomNaviView
import com.soumya.wwdablu.hungry.databinding.ActivityRestaurantDetailsBinding
import com.soumya.wwdablu.hungry.fragment.resdetails.OverviewFragment
import com.soumya.wwdablu.hungry.fragment.resdetails.PhotosFragment
import com.soumya.wwdablu.hungry.fragment.resdetails.ReviewFragment
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.viewmodel.RestaurantDetailsViewModel
import java.util.*

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityRestaurantDetailsBinding
    private lateinit var mRestaurant: RestaurantInfo

    private var mRestoreIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mRestoreIndex = savedInstanceState?.getInt("index", 0) ?: 0

        val restaurantDetailsViewModel = ViewModelProvider(this).get(RestaurantDetailsViewModel::class.java)

        mViewBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)

        mViewBinding.bnvBottomMenu.setOnItemSelectedListener(navigationItemClickListener)
        mViewBinding.bnvBottomMenu.visibility = View.GONE

        var resInfo: RestaurantInfo? = intent.getParcelableExtra("res_details")
        if(resInfo != null && restaurantDetailsViewModel.getRestaurantInfo() == null) {
            restaurantDetailsViewModel.setRestaurantInfo(resInfo)
        } else if (resInfo == null && restaurantDetailsViewModel.getRestaurantInfo() != null) {
            resInfo = restaurantDetailsViewModel.getRestaurantInfo()
        }

        if(resInfo != null) {

            mRestaurant = resInfo

            mViewBinding.bnvBottomMenu.visibility = View.VISIBLE
            mViewBinding.bnvBottomMenu.selectedItemId = RestaurantDetailsBottomNaviView
                    .MenuItems.Overview.ordinal

            setContentView(mViewBinding.root)

            mViewBinding.bnvBottomMenu.selectedItemId = mRestoreIndex
        } else {
            finish()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("index", mViewBinding.bnvBottomMenu.selectedItemId)
    }

    private val navigationItemClickListener: NavigationBarView.OnItemSelectedListener
            = NavigationBarView.OnItemSelectedListener {

        if(!this::mRestaurant.isInitialized) {
            return@OnItemSelectedListener true
        }

        val menuItem: RestaurantDetailsBottomNaviView.MenuItems = RestaurantDetailsBottomNaviView.MenuItems
                .values()[it.itemId]

        val useFragment: Fragment = when (menuItem) {

            RestaurantDetailsBottomNaviView.MenuItems.Overview -> {
                OverviewFragment.newInstance()
            }

            RestaurantDetailsBottomNaviView.MenuItems.Menu -> {
                PhotosFragment.newInstance()
            }

            RestaurantDetailsBottomNaviView.MenuItems.Photos -> {
                PhotosFragment.newInstance()
            }

            RestaurantDetailsBottomNaviView.MenuItems.Reviews -> {
                ReviewFragment.newInstance()
            }
        }

        supportFragmentManager.beginTransaction()
                .replace(mViewBinding.flFragContainer.id, useFragment, useFragment::class.java.simpleName)
                .commitAllowingStateLoss()

        return@OnItemSelectedListener true
    }
}