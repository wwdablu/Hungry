package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.navigation.NavigationBarView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.customview.DashboardBottomNaviView
import com.soumya.wwdablu.hungry.databinding.ActivityDashboardBinding
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.defines.SearchBy
import com.soumya.wwdablu.hungry.fragment.RecommendedFragment
import com.soumya.wwdablu.hungry.fragment.GenericSearchResultFragment
import com.soumya.wwdablu.hungry.fragment.ProfileFragment
import com.soumya.wwdablu.hungry.viewmodel.DashboardViewModel
import com.soumya.wwdablu.hungry.viewmodel.GenericSearchResultViewModel
import com.soumya.wwdablu.hungry.viewmodel.RecommendedViewModel
import java.util.*

class DashboardActivity : AppCompatActivity(), DashboardBottomNaviView.DashboardBottomNaviViewCallback {

    private lateinit var mViewBinding: ActivityDashboardBinding

    private lateinit var mViewModel: DashboardViewModel
    private lateinit var mRecommendedViewModel: RecommendedViewModel
    private lateinit var mGenericSearchResultViewModel: GenericSearchResultViewModel

    private var mRestoreIndex: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityDashboardBinding.inflate(layoutInflater)

        mRestoreIndex = savedInstanceState?.getInt("index",
            CategoryEnum.Recommended.ordinal) ?: -1

        mViewBinding.bnvBottomMenu.setCallback(this)
        mViewBinding.bnvBottomMenu.setOnItemSelectedListener(navigationItemClickListener)

        setContentView(mViewBinding.root)

        mViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        mRecommendedViewModel = ViewModelProvider(this).get(RecommendedViewModel::class.java)
        mGenericSearchResultViewModel = ViewModelProvider(this).get(GenericSearchResultViewModel::class.java)
    }

    override fun onMenuPrepared() {

        if(mRestoreIndex == -1) {
            mViewBinding.bnvBottomMenu.selectedItemId = CategoryEnum.Recommended.ordinal
        } else {
            mViewBinding.bnvBottomMenu.selectedItemId = mRestoreIndex
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("index", mViewBinding.bnvBottomMenu.selectedItemId)
    }

    private val navigationItemClickListener: NavigationBarView.OnItemSelectedListener
            = NavigationBarView.OnItemSelectedListener {

        when (it.itemId) {
            CategoryEnum.Recommended.ordinal -> {

                if(mRestoreIndex != CategoryEnum.Recommended.ordinal) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_frag_container, RecommendedFragment.newInstance(), RecommendedFragment::class.java.simpleName)
                        .commitAllowingStateLoss()
                }
            }

            DashboardBottomNaviView.ProfileMenu -> {

                if(mRestoreIndex != DashboardBottomNaviView.ProfileMenu) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_frag_container, ProfileFragment.newInstance(), RecommendedFragment::class.java.simpleName)
                        .commitAllowingStateLoss()
                }
            }

            else -> {

                if(mRestoreIndex != it.itemId) {

                    val catEnum: CategoryEnum = CategoryEnum.values()[it.itemId-1]
                    val catFrag = GenericSearchResultFragment.newInstance(SearchBy.Category, catEnum.name,
                        SearchBy.Collection, "1")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_frag_container, catFrag, GenericSearchResultFragment::class.java.simpleName)
                        .commitAllowingStateLoss()
                }
            }
        }

        return@OnItemSelectedListener true
    }
}