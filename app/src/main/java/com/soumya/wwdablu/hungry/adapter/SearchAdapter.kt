package com.soumya.wwdablu.hungry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soumya.wwdablu.hungry.databinding.ItemSearchCuisineBinding
import com.soumya.wwdablu.hungry.databinding.ItemSearchResBinding
import com.soumya.wwdablu.hungry.databinding.ItemSearchResHeaderBinding
import com.soumya.wwdablu.hungry.iface.CuisineItemSelector
import com.soumya.wwdablu.hungry.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.model.network.cuisine.Cuisine
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import java.util.*

class SearchAdapter(cuisineList: List<Cuisine>, searchModel: SearchModel,
                    resListener: RestaurantItemSelector, cuisineItemSelector: CuisineItemSelector) :
        RecyclerView.Adapter<SearchAdapter.BaseSearchViewHolder>() {

    private val mResListener: RestaurantItemSelector = resListener
    private val mCuisineItemSelector: CuisineItemSelector = cuisineItemSelector

    private enum class ViewType {
        Cuisine,
        RestaurantHeader,
        Restaurant
    }

    private val mCuisineList: LinkedList<Cuisine> = LinkedList(cuisineList)
    private var mSearchModel: SearchModel? = searchModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseSearchViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        return when (ViewType.values()[viewType]) {
            ViewType.Cuisine -> {
                SearchCuisineViewHolder(ItemSearchCuisineBinding.inflate(inflater, parent, false))
            }
            ViewType.Restaurant -> {
                SearchResViewHolder(ItemSearchResBinding.inflate(inflater, parent, false))
            }
            ViewType.RestaurantHeader -> {
                SearchResHeaderViewHolder(ItemSearchResHeaderBinding.inflate(inflater, parent, false))
            }
        }
    }

    override fun onBindViewHolder(holder: BaseSearchViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int {
        return mCuisineList.size + (mSearchModel?.restaurants?.size ?: 0) +
                if(mSearchModel == null) 0 else 1
    }

    override fun getItemViewType(position: Int): Int {

        return when {
            mCuisineList.size >= position + 1 -> {
                ViewType.Cuisine.ordinal
            }
            position == mCuisineList.size -> {
                ViewType.RestaurantHeader.ordinal
            }
            else -> {
                ViewType.Restaurant.ordinal
            }
        }
    }

    fun setSearchResults(list: List<Cuisine>, searchResult: SearchModel?) {

        mCuisineList.clear()
        mCuisineList.addAll(list)

        mSearchModel = searchResult

        notifyDataSetChanged()
    }

    inner class SearchCuisineViewHolder(viewBinding: ItemSearchCuisineBinding) :
            BaseSearchViewHolder(viewBinding.root) {

        private val mViewBinding: ItemSearchCuisineBinding = viewBinding

        init {
            mViewBinding.root.setOnClickListener {
                mCuisineItemSelector.onCuisineClicked(mCuisineList[adapterPosition])
            }
        }

        override fun bind(position: Int) {

            mViewBinding.cuisine = mCuisineList[position]
        }
    }

    inner class SearchResViewHolder(viewBinding: ItemSearchResBinding) :
            BaseSearchViewHolder(viewBinding.root) {

        private val mViewBinding: ItemSearchResBinding = viewBinding

        init {
            mViewBinding.root.setOnClickListener {
                val restaurant = getRestaurant() ?: return@setOnClickListener
                mResListener.onRestaurantClicked(restaurant)
            }
        }

        override fun bind(position: Int) {

            val restaurant = getRestaurant() ?: return
            mViewBinding.resInfo = restaurant

            RestaurantInfoUtil.loadFeatureImage(mViewBinding.ivCuisine, restaurant, false)
        }

        private fun getRestaurant() : RestaurantInfo? {
            val index = adapterPosition - mCuisineList.size - 1
            return mSearchModel?.restaurants?.get(index)?.restaurant
        }
    }

    inner class SearchResHeaderViewHolder(viewBinding: ItemSearchResHeaderBinding) :
            BaseSearchViewHolder(viewBinding.root) {

        override fun bind(position: Int) {
            //
        }
    }

    abstract class BaseSearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(position: Int)
    }
}