package com.soumya.wwdablu.hungry.fragment.allcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.soumya.wwdablu.hungry.databinding.FragCategoryGenericBinding
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers

class GenericCategoryFragment(category: CategoryEnum) : Fragment() {

    private val mCategoryEnum: CategoryEnum = category
    private lateinit var mViewBinding: FragCategoryGenericBinding
    private lateinit var mGenericSearchModelAdapter: GenericSearchModelAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        mViewBinding = FragCategoryGenericBinding.inflate(inflater)

        mViewBinding.rvCatList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        getByCategory()

        return mViewBinding.root
    }

    private fun getByCategory() {

        HungryRepo.searchByCategoryId(mCategoryEnum)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<SearchModel>() {
                override fun onNext(t: SearchModel?) {

                    if(t == null) {
                        return
                    }

                    mGenericSearchModelAdapter = GenericSearchModelAdapter(t)
                }

                override fun onError(e: Throwable?) {
                    //
                }

                override fun onComplete() {
                    mViewBinding.rvCatList.adapter = mGenericSearchModelAdapter
                }
            })
    }
}