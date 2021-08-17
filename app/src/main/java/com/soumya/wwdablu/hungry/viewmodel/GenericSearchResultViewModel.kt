package com.soumya.wwdablu.hungry.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import kotlinx.coroutines.launch

class GenericSearchResultViewModel : HungryViewModel() {

    private val mSearchModelCategory: MutableLiveData<SearchModel> = MutableLiveData()
    private val mSearchModelGeneral: MutableLiveData<SearchModel> = MutableLiveData()

    fun getByCategory(categoryEnum: CategoryEnum) : LiveData<SearchModel> {

        if(mSearchModelCategory.value == null) {
            viewModelScope.launch(defaultExceptionHandler) {
                val t = HungryRepo.searchByCategoryId(categoryEnum)
                mSearchModelCategory.postValue(t)
//            getByCollectionId(if(mFallbackSearchCriteria.second.isEmptyOrBlank()) 1 else
//                mFallbackSearchCriteria.second.toInt())
            }
        }

        return mSearchModelCategory
    }

    fun getByCollectionId(collectionId: Int) : LiveData<SearchModel> {

        viewModelScope.launch(defaultExceptionHandler) {
            val t = HungryRepo.searchByCollectionId(collectionId)
            mSearchModelGeneral.postValue(t)
        }

        return mSearchModelGeneral
    }

    fun getByCuisineId(cuisineId: String) : LiveData<SearchModel> {

        viewModelScope.launch(defaultExceptionHandler) {
            val t = HungryRepo.searchByCuisineId(cuisineId)
            mSearchModelGeneral.postValue(t)
        }

        return mSearchModelGeneral
    }

    fun getByQuery(query: String) : LiveData<SearchModel> {

        viewModelScope.launch(defaultExceptionHandler) {
            val t = HungryRepo.search(query)
            mSearchModelGeneral.postValue(t)
        }

        return mSearchModelGeneral
    }
}