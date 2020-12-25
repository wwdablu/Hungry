package com.soumya.wwdablu.hungry.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.enums.SearchBy
import com.soumya.wwdablu.hungry.fragment.GenericSearchResultFragment

class GenericSearchResultActivity : HungryActivity() {

    companion object {
        fun createLaunchIntent(context: Context, primarySearch: SearchBy, primarySearchParam: String,
               fallbackSearch: SearchBy = SearchBy.None, fallbackSearchParam: String = "") : Intent {

            val intent = Intent(context, GenericSearchResultActivity::class.java)

            val bundle = Bundle()
            bundle.putSerializable("key.primary_search", primarySearch)
            bundle.putString("key.primary_search_param", primarySearchParam)
            bundle.putSerializable("key.fallback_search", fallbackSearch)
            bundle.putString("key.fallback_search_param", fallbackSearchParam)

            intent.putExtras(bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generic_search_result)

        val fragment = GenericSearchResultFragment.newInstance(intent.extras!!)
        supportFragmentManager.beginTransaction()
            .add(R.id.fl_frag_container, fragment, GenericSearchResultFragment::class.java.simpleName)
            .addToBackStack(GenericSearchResultFragment::class.java.simpleName)
            .commitAllowingStateLoss()
    }

    override fun onBackPressed() {

        if(supportFragmentManager.backStackEntryCount == 1) {
            finish()
            return
        }

        super.onBackPressed()
    }
}