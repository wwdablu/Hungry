package com.soumya.wwdablu.hungry.activity.common

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class HungryActivity : AppCompatActivity() {

    protected val ioScope = CoroutineScope(Dispatchers.IO)
    protected val mainScope = CoroutineScope(Dispatchers.Main)

    protected val defaultExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
        onCoroutineException(coroutineContext, throwable)
    }

    protected open fun onCoroutineException(coroutineContext: CoroutineContext, throwable: Throwable) {
        //Nothing by default
    }

    protected fun hideKeyboard() {
        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    protected fun loadImageIntoImageView(imageUrl: String, imageView: ImageView) {

        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.default_card_bg)
            .into(imageView)
    }
}