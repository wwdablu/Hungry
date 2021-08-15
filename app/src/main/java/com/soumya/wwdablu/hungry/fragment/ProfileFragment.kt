package com.soumya.wwdablu.hungry.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.soumya.wwdablu.hungry.databinding.FragProfileBinding

class ProfileFragment : HungryFragment<FragProfileBinding>() {

    companion object {

        fun newInstance() : ProfileFragment {
            return ProfileFragment()
        }
    }

    override fun onCreateViewExt(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragProfileBinding.inflate(inflater, container, false)

        return mViewBinding.root
    }
}