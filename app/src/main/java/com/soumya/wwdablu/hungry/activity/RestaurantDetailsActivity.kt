package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.customview.RestaurantDetailsBottomNaviView
import com.soumya.wwdablu.hungry.databinding.ActivityRestaurantDetailsBinding
import com.soumya.wwdablu.hungry.fragment.resdetails.OverviewFragment
import com.soumya.wwdablu.hungry.fragment.resdetails.RestaurantPhotosFragment
import com.soumya.wwdablu.hungry.model.network.search.Restaurant
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
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

        getRestaurantDetails(intent.getStringExtra("resid"))

        setContentView(mViewBinding.root)
    }

    private fun getRestaurantDetails(resId: String?) {

        if(resId == null || resId.isEmpty() || resId.isBlank()) {
            finish()
            return
        }

        HungryRepo.getRestaurantDetails(resId.toInt())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : DisposableObserver<RestaurantInfo>() {
                override fun onNext(t: RestaurantInfo?) {
                    if (t != null) {
                        mRestaurant = t
                    }
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                    finish()
                }

                override fun onComplete() {

                    if(this@RestaurantDetailsActivity::mRestaurant.isInitialized) {
                        mViewBinding.bnvBottomMenu.visibility = View.VISIBLE
                        mViewBinding.bnvBottomMenu.selectedItemId = RestaurantDetailsBottomNaviView
                                .MenuItems.Overview.ordinal
                    } else {
                        finish()
                    }
                }
            })
    }

    private val navigationItemClickListener: BottomNavigationView.OnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {

        val menuItem: RestaurantDetailsBottomNaviView.MenuItems = RestaurantDetailsBottomNaviView.MenuItems
                .values()[it.itemId]

        val useFragment: Fragment = when (menuItem) {

            RestaurantDetailsBottomNaviView.MenuItems.Overview -> {
                mFragments[menuItem] ?: OverviewFragment(mRestaurant)
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