package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.customview.DashboardBottomNaviView
import com.soumya.wwdablu.hungry.databinding.ActivityDashboardBinding
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.defines.SearchBy
import com.soumya.wwdablu.hungry.fragment.RecommendedFragment
import com.soumya.wwdablu.hungry.fragment.GenericSearchResultFragment
import com.soumya.wwdablu.hungry.fragment.ProfileFragment
import java.util.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityDashboardBinding

    private val mCategoryFragmentMap: EnumMap<CategoryEnum, Fragment> = EnumMap(CategoryEnum::class.java)
    private lateinit var mRecommendedFragment: RecommendedFragment
    private lateinit var mProfileFragment: ProfileFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityDashboardBinding.inflate(layoutInflater)

        mViewBinding.bnvBottomMenu.setOnNavigationItemSelectedListener(navigationItemClickListener)

        setContentView(mViewBinding.root)
    }

    private val navigationItemClickListener: BottomNavigationView.OnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {

        when (it.itemId) {
            CategoryEnum.Recommended.ordinal -> {

                if(!this::mRecommendedFragment.isInitialized) {
                    mRecommendedFragment = RecommendedFragment.newInstance()
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag_container, mRecommendedFragment, RecommendedFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
            }

            DashboardBottomNaviView.ProfileMenu -> {

                if(!this::mProfileFragment.isInitialized) {
                    mProfileFragment = ProfileFragment.newInstance()
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag_container, mProfileFragment, ProfileFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
            }

            else -> {

                val catEnum: CategoryEnum = CategoryEnum.values()[it.itemId-1]
                var catFrag: Fragment? = mCategoryFragmentMap[catEnum]
                if(catFrag == null) {
                    catFrag = GenericSearchResultFragment.newInstance(SearchBy.Category, catEnum.name,
                            SearchBy.Collection, "1")
                    mCategoryFragmentMap[catEnum] = catFrag
                }
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag_container, catFrag, GenericSearchResultFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
            }
        }

        return@OnNavigationItemSelectedListener true
    }
}