package com.soumya.wwdablu.hungry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class HungryFragment<T : ViewBinding> : Fragment() {

    protected lateinit var mViewBinding: T

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if(this::mViewBinding.isInitialized) {
            return mViewBinding.root
        }

        return onCreateViewExt(inflater, container, savedInstanceState)
    }

    abstract fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
}