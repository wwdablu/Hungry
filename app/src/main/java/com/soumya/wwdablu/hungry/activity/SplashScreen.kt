package com.soumya.wwdablu.hungry.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.database.HungryDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.*

class SplashScreen : HungryActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        ioScope.launch(defaultExceptionHandler) {
            proceed()
        }
    }

    override fun onLocationUpdated(location: Location?) {

        ioScope.launch {
            val city = HungryRepo.getCity()
            val size = city.size
        }.invokeOnCompletion {
            mainScope.launch {
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
                fetchCurrentCoordinates()
            }
        }
    }
}