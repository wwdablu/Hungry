package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.activity.common.HungryActivity
import com.soumya.wwdablu.hungry.activity.common.LocationProviderActivity
import com.soumya.wwdablu.hungry.databinding.ActivityLocationAccessBinding
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LocationAccess : LocationProviderActivity() {

    private lateinit var mViewBinding: ActivityLocationAccessBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityLocationAccessBinding.inflate(layoutInflater)

        mViewBinding.btnAutoLocation.setOnClickListener {
            fetchCurrentLocation()
        }

        setContentView(mViewBinding.root)
    }

    override fun onLocationUpdated(location: Location) {

        ioScope.launch(defaultExceptionHandler) {
            HungryRepo.getCity()
        }.invokeOnCompletion {
            CoroutineScope(Dispatchers.Main).launch(defaultExceptionHandler) {
                startActivity(Intent(this@LocationAccess,
                    DashboardActivity::class.java))
                finish()
            }
        }
    }
}