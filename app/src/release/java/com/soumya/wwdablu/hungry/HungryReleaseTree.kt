package com.soumya.wwdablu.hungry

import android.util.Log

class HungryReleaseTree : Timber.Tree() {
    @Override
    protected fun isLoggable(tag: String?, priority: Int): Boolean {
        when (priority) {
            Log.VERBOSE, Log.DEBUG, Log.INFO -> return false
        }
        return super.isLoggable(tag, priority)
    }

    @Override
    protected fun log(priority: Int, tag: String?, message: String?, t: Throwable?) {
        super.log(priority, tag.toString() + " : " + message, t)
    }
}