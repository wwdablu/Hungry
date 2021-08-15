package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.customview.DashboardBottomNaviView
import com.soumya.wwdablu.hungry.databinding.ActivityDashboardBinding
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.defines.SearchBy
import com.soumya.wwdablu.hungry.fragment.RecommendedFragment
import com.soumya.wwdablu.hungry.fragment.GenericSearchResultFragment
import com.soumya.wwdablu.hungry.fragment.ProfileFragment
import com.soumya.wwdablu.hungry.viewmodel.DashboardViewModel
import com.soumya.wwdablu.hungry.viewmodel.RecommendedViewModel
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityDashboardBinding

    private lateinit var mViewModel: DashboardViewModel
    private lateinit var mRecommendedViewModel: RecommendedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityDashboardBinding.inflate(layoutInflater)

        mViewBinding.bnvBottomMenu.setOnNavigationItemSelectedListener(navigationItemClickListener)

        setContentView(mViewBinding.root)

        mViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        mRecommendedViewModel = ViewModelProvider(this).get(RecommendedViewModel::class.java)
    }

    //The EnumMap is not good, needs to be handled

    private val navigationItemClickListener: BottomNavigationView.OnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {

        when (it.itemId) {
            CategoryEnum.Recommended.ordinal -> {

                if(mViewModel.bottomIndex.value != CategoryEnum.Recommended.ordinal) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_frag_container, RecommendedFragment.newInstance(), RecommendedFragment::class.java.simpleName)
                        .commitAllowingStateLoss()
                    mViewModel.setBottomIndex(CategoryEnum.Recommended.ordinal)
                }
            }

            DashboardBottomNaviView.ProfileMenu -> {

                if(mViewModel.bottomIndex.value != DashboardBottomNaviView.ProfileMenu) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_frag_container, ProfileFragment.newInstance(), RecommendedFragment::class.java.simpleName)
                        .commitAllowingStateLoss()
                    mViewModel.setBottomIndex(DashboardBottomNaviView.ProfileMenu)
                }
            }

            else -> {

                if(mViewModel.bottomIndex.value != it.itemId) {

                    val catEnum: CategoryEnum = CategoryEnum.values()[it.itemId-1]
                    val catFrag = GenericSearchResultFragment.newInstance(SearchBy.Category, catEnum.name,
                        SearchBy.Collection, "1")
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fl_frag_container, catFrag, GenericSearchResultFragment::class.java.simpleName)
                        .commitAllowingStateLoss()
                    mViewModel.setBottomIndex(it.itemId)
                }
            }
        }

        return@OnNavigationItemSelectedListener true
    }
}