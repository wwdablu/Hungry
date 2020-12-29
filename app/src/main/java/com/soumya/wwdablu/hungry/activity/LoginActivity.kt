package com.soumya.wwdablu.hungry.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.MainThread
import com.soumya.wwdablu.hungry.database.HungryDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.databinding.ActivityLoginBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {

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

        Observable.create<UserInfo> {
            val user:UserInfo? = HungryDatabase.getDB(context).UserInfoDao().isUserRegistered(userId)
            if(user == null) {
                HungryDatabase.getDB(context).UserInfoDao().registerLoggedInUser(UserInfo(
                        isLoggedIn = true, userIdentifier = userId))
            } else {
                user.isLoggedIn = true
                HungryDatabase.getDB(context).UserInfoDao().updateLoginStatus(user)
            }

            it.onNext(user)
            it.onComplete()

        }.observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<UserInfo>() {
                override fun onNext(t: UserInfo?) {
                    //
                }

                override fun onError(e: Throwable?) {
                    //
                }

                override fun onComplete() {
                    launchLocationAccessPermissionActivity(this@LoginActivity)
                    finish()
                }
            })
    }
}