package com.soumya.wwdablu.hungry.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.defines.CategoryEnum
import com.soumya.wwdablu.hungry.model.network.categories.Categories
import com.soumya.wwdablu.hungry.repository.HungryRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.util.*

class DashboardBottomNaviView : BottomNavigationView {

    private lateinit var mCategoriesList: List<Categories>

    constructor(context: Context) : super(context) {
        fetchCategories()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        fetchCategories()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        fetchCategories()
    }

    private fun prepareMenu() {

        val pair: Pair<CategoryEnum, CategoryEnum?> = getFoodType()

        menu.add(Menu.NONE, CategoryEnum.Recommended.ordinal, Menu.NONE, "Order")
                .setIcon(R.drawable.ic_recommended)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        selectedItemId = CategoryEnum.Recommended.ordinal

        for(categories: Categories in mCategoriesList) {

            if(pair.first.id == categories.category.id) {

                menu.add(Menu.NONE, categories.category.id, Menu.NONE,categories.category.name)
                        .setIcon(pair.first.icon())
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }

            else if(pair.second != null && pair.second!!.id == categories.category.id) {

                menu.add(Menu.NONE, categories.category.id, Menu.NONE,categories.category.name)
                        .setIcon(pair.second!!.icon())
                        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            }
        }
    }

    private fun getFoodType() : Pair<CategoryEnum, CategoryEnum?> {

        val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
        return when (hourOfDay) {

            in 0..4 -> {
                Pair(CategoryEnum.Nightlife, CategoryEnum.PubAndBar)
            }

            in 5..9 -> {
                Pair(CategoryEnum.Breakfast, null)
            }

            in 10..11 -> {
                Pair(CategoryEnum.Breakfast, CategoryEnum.Lunch)
            }

            in 12..15 -> {
                Pair(CategoryEnum.Lunch, null)
            }

            in 16..18 -> {
                Pair(CategoryEnum.Cafes, CategoryEnum.CatchingUp)
            }

            in 19..23 -> {
                Pair(CategoryEnum.Dinner, null)
            }

            else -> {
                Pair(CategoryEnum.PubAndBar, CategoryEnum.Nightlife)
            }
        }
    }

    private fun fetchCategories() {

        HungryRepo.getCategories()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object: DisposableObserver<List<Categories>>() {
                override fun onNext(t: List<Categories>?) {
                    mCategoriesList = t ?: LinkedList()
                }

                override fun onError(e: Throwable?) {
                    Timber.e(e)
                }

                override fun onComplete() {
                    prepareMenu()
                }
            })
    }
}