package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ActivityDashboardBinding
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.fragment.DiningFragment
import com.soumya.wwdablu.hungry.fragment.allcategory.GenericCategoryFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityDashboardBinding.inflate(layoutInflater)

        mViewBinding.bnvBottomMenu.setOnNavigationItemSelectedListener(navigationItemClickListener)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_frag_container, DiningFragment(), DiningFragment::class.java.simpleName)
                .commitAllowingStateLoss()

        setContentView(mViewBinding.root)
    }

    private val navigationItemClickListener: BottomNavigationView.OnNavigationItemSelectedListener
            = BottomNavigationView.OnNavigationItemSelectedListener {

        when (it.itemId) {
            R.id.menu_dining -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag_container, DiningFragment(), DiningFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
            }

            R.id.menu_nightlife -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fl_frag_container, GenericCategoryFragment(CategoryEnum.Nightlife),
                            GenericCategoryFragment::class.java.simpleName)
                    .commitAllowingStateLoss()
            }
        }

        return@OnNavigationItemSelectedListener true
    }
}