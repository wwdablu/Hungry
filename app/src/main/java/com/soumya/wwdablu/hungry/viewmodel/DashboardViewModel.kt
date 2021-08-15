package com.soumya.wwdablu.hungry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DashboardViewModel : HungryViewModel() {

    private val mBottomIndex: MutableLiveData<Int> = MutableLiveData(999)
    val bottomIndex: LiveData<Int> = mBottomIndex

    fun setBottomIndex(index: Int) {
        mBottomIndex.postValue(index)
    }
}