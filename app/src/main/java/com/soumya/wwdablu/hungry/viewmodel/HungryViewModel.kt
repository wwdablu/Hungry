package com.soumya.wwdablu.hungry.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import timber.log.Timber
import kotlin.coroutines.CoroutineContext

abstract class HungryViewModel : ViewModel() {

    protected val ioScope = CoroutineScope(Dispatchers.IO)
    protected val mainScope = CoroutineScope(Dispatchers.Main)

    protected val defaultExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
        onCoroutineException(coroutineContext, throwable)
    }

    protected open fun onCoroutineException(coroutineContext: CoroutineContext, throwable: Throwable) {
        //Nothing by default
    }
}