package com.soumya.wwdablu.hungry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soumya.wwdablu.hungry.network.model.collections.CuratedCollection
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.launch

class RecommendedViewModel : HungryViewModel() {

    private val mCuratedCollection: MutableLiveData<List<CuratedCollection>> = MutableLiveData()
    val curatedCollection: LiveData<List<CuratedCollection>> = mCuratedCollection

    private val mRecommendation: MutableLiveData<SearchModel> = MutableLiveData()
    val recommendation: LiveData<SearchModel> = mRecommendation

    fun fetchCollection() {

        if(mCuratedCollection.value != null && mCuratedCollection.value!!.isNotEmpty()) {
            return
        }

        viewModelScope.launch(defaultExceptionHandler) {
            val t: List<CuratedCollection> = HungryRepo.getCollections()
            mCuratedCollection.postValue(t)
        }
    }

    fun fetchRecommendation() {

        if(mRecommendation.value != null) {
            return
        }

        viewModelScope.launch(defaultExceptionHandler) {
            val t: SearchModel = HungryRepo.searchByCollectionId(1)
            mRecommendation.postValue(t)
        }
    }
}