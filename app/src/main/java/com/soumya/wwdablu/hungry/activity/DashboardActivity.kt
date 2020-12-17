package com.soumya.wwdablu.hungry.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ActivityDashboardBinding
import com.soumya.wwdablu.hungry.fragment.DiningFragment

class DashboardActivity : AppCompatActivity() {

    private lateinit var mViewBinding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityDashboardBinding.inflate(layoutInflater)

        supportFragmentManager.beginTransaction()
                .replace(R.id.fl_frag_container, DiningFragment(), DiningFragment::class.java.simpleName)
                .commitAllowingStateLoss()

        setContentView(mViewBinding.root)
    }
}