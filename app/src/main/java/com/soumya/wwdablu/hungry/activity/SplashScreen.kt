package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.activity.common.HungryActivity
import com.soumya.wwdablu.hungry.activity.common.LocationProviderActivity
import com.soumya.wwdablu.hungry.database.HungryDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SplashScreen : LocationProviderActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onStart() {
        super.onStart()
        ioScope.launch(defaultExceptionHandler) {
            proceed()
        }
    }

    override fun onStop() {
        super.onStop()
        ioScope.coroutineContext.cancelChildren()
    }

    override fun onCoroutineException(coroutineContext: CoroutineContext, throwable: Throwable) {
        finish()
    }

    override fun onLocationUpdated(location: Location) {

        ioScope.launch(defaultExceptionHandler) {
            HungryRepo.getCity()
        }.invokeOnCompletion {
            mainScope.launch(defaultExceptionHandler) {
                startActivity(Intent(this@SplashScreen,
                    DashboardActivity::class.java))
                finish()
            }
        }
    }

    private suspend fun proceed() {
        val user: UserInfo? = HungryDatabase.getDB(this@SplashScreen)
                .UserInfoDao().getLoggedUser()

        //Just to show the fancy Splash screen
        delay(1000)

        withContext(Dispatchers.Main) {
            if(user == null) {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
                finish()
            } else {
                fetchCurrentLocation()
            }
        }
    }
}