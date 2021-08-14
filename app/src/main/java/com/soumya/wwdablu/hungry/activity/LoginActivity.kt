package com.soumya.wwdablu.hungry.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.MainThread
import com.soumya.wwdablu.hungry.database.HungryDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.databinding.ActivityLoginBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber

class LoginActivity : HungryActivity() {

    private lateinit var mViewBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mViewBinding = ActivityLoginBinding.inflate(layoutInflater)

        mViewBinding.btnGetOtp.setOnClickListener {

            val inputPhone: String = mViewBinding.piPhoneNumber.getSelectedData().second
            if(inputPhone.isBlank() || inputPhone.isEmpty()) {
                return@setOnClickListener
            }

            registerUser(it.context, inputPhone)
        }

        mViewBinding.btnSkip.setOnClickListener {
            launchLocationAccessPermissionActivity(it.context)
        }

        setContentView(mViewBinding.root)
    }

    @MainThread
    private fun launchLocationAccessPermissionActivity(context: Context) {
        startActivity(Intent(context, LocationAccess::class.java))
    }

    private fun registerUser(context: Context, userId: String) {

        val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
            Timber.e(throwable)
            finish()
        }

        ioScope.launch(exceptionHandler) {
            val user:UserInfo? = HungryDatabase.getDB(context).UserInfoDao().isUserRegistered(userId)
            if(user == null) {
                HungryDatabase.getDB(context).UserInfoDao().registerLoggedInUser(UserInfo(
                    isLoggedIn = true, userIdentifier = userId))
            } else {
                user.isLoggedIn = true
                HungryDatabase.getDB(context).UserInfoDao().updateLoginStatus(user)
            }
        }.invokeOnCompletion {
            mainScope.launch(exceptionHandler) {
                launchLocationAccessPermissionActivity(this@LoginActivity)
                finish()
            }
        }
    }
}