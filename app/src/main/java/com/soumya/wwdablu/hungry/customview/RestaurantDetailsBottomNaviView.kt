package com.soumya.wwdablu.hungry.customview

import android.content.Context
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.soumya.wwdablu.hungry.R

class RestaurantDetailsBottomNaviView : BottomNavigationView {

    enum class MenuItems {
        Overview,
        Menu,
        Photos,
        Reviews
    }

    constructor(context: Context) : super(context) {
        prepareMenu()
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        prepareMenu()
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int) : super(context, attributeSet, defStyleAttr) {
        prepareMenu()
    }

    private fun prepareMenu() {

        menu.add(Menu.NONE, MenuItems.Overview.ordinal, Menu.NONE, context.getString(R.string.menu_overview))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        menu.add(Menu.NONE, MenuItems.Menu.ordinal, Menu.NONE, context.getString(R.string.menu_foodmenu))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        menu.add(Menu.NONE, MenuItems.Photos.ordinal, Menu.NONE, context.getString(R.string.menu_photos))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)

        menu.add(Menu.NONE, MenuItems.Reviews.ordinal, Menu.NONE, context.getString(R.string.menu_reviews))
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
    }
}