package com.soumya.wwdablu.hungry.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class HungryFragment<T : ViewBinding> : Fragment() {

    protected lateinit var mViewBinding: T

    protected fun isScreenInPortrait() : Boolean {
        return resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT
    }

    protected fun String.isEmptyOrBlank() : Boolean {
        return isEmpty() || isBlank()
    }

    protected fun String.isNotEmptyAndNotBlank() : Boolean {
        return isNotEmpty() && isNotBlank()
    }
}