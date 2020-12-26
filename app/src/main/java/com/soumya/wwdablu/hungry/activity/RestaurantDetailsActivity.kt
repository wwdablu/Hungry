package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.customview.RestaurantDetailsBottomNaviView
import com.soumya.wwdablu.hungry.databinding.ActivityRestaurantDetailsBinding
import com.soumya.wwdablu.hungry.fragment.resdetails.RestaurantOverviewFragment
import com.soumya.wwdablu.hungry.fragment.resdetails.RestaurantPhotosFragment
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import java.util.*

class RestaurantDetailsActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityRestaurantDetailsBinding
    private lateinit var mRestaurant: RestaurantInfo

    private val mFragments: EnumMap<RestaurantDetailsBottomNaviView.MenuItems, Fragment> = EnumMap(
            RestaurantDetailsBottomNaviView.MenuItems::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityRestaurantDetailsBinding.inflate(layoutInflater)

        mViewBinding.bnvBottomMenu.setOnNavigationItemSelectedListener(navigationItemClickListener)
        mViewBinding.bnvBottomMenu.visibility = View.GONE

        val resInfo: RestaurantInfo? = intent.getParcelableExtra("res_details")

        if(resInfo != null) {

            mRestaurant = resInfo

            mViewBinding.bnvBottomMenu.visibility = View.VISIBLE
            mViewBinding.bnvBottomMenu.selectedItemId = RestaurantDetailsBottomNaviView
                    .MenuItems.Overview.ordinal

            setContentView(mViewBinding.root)
        } else {
            finish()
        }
    }

    private val navigationItemClickListener: BottomNavigationView.OnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {

        if(!this::mRestaurant.isInitialized) {
            return@OnNavigationItemSelectedListener true
        }

        val menuItem: RestaurantDetailsBottomNaviView.MenuItems = RestaurantDetailsBottomNaviView.MenuItems
                .values()[it.itemId]

        val useFragment: Fragment = when (menuItem) {

            RestaurantDetailsBottomNaviView.MenuItems.Overview -> {
                mFragments[menuItem] ?: RestaurantOverviewFragment.newInstance(mRestaurant)
            }

            RestaurantDetailsBottomNaviView.MenuItems.Menu -> {
                mFragments[menuItem] ?: RestaurantPhotosFragment(mRestaurant)
            }

            RestaurantDetailsBottomNaviView.MenuItems.Photos -> {
                mFragments[menuItem] ?: RestaurantPhotosFragment(mRestaurant)
            }

            RestaurantDetailsBottomNaviView.MenuItems.Reviews -> {
                mFragments[menuItem] ?: RestaurantPhotosFragment(mRestaurant)
            }
        }

        if(!mFragments.containsKey(menuItem)) {
            mFragments[menuItem] = useFragment
        }

        supportFragmentManager.beginTransaction()
                .replace(mViewBinding.flFragContainer.id, useFragment, useFragment::class.java.simpleName)
                .commitAllowingStateLoss()

        return@OnNavigationItemSelectedListener true
    }
}