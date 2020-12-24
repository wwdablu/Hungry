package com.soumya.wwdablu.hungry.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soumya.wwdablu.hungry.databinding.ItemSearchCuisineBinding
import com.soumya.wwdablu.hungry.databinding.ItemSearchResBinding
import com.soumya.wwdablu.hungry.databinding.ItemSearchResHeaderBinding
import com.soumya.wwdablu.hungry.fragment.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.model.network.cuisine.Cuisine
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil

class SearchAdapter(cuisineList: List<Cuisine>, searchModel: SearchModel,
                    resListener: RestaurantItemSelector) :
        RecyclerView.Adapter<SearchAdapter.BaseSearchViewHolder>() {

    private val mResListener: RestaurantItemSelector = resListener

    private enum class ViewType {
        Cuisine,
        RestaurantHeader,
        Restaurant
    }

    private val mCuisineList: List<Cuisine> = cuisineList
    private val mSearchModel: SearchModel = searchModel

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
        return mCuisineList.size + mSearchModel.restaurants.size + 1
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

    inner class SearchCuisineViewHolder(viewBinding: ItemSearchCuisineBinding) :
            BaseSearchViewHolder(viewBinding.root) {

        private val mViewBinding: ItemSearchCuisineBinding = viewBinding

        override fun bind(position: Int) {

            mViewBinding.cuisine = mCuisineList[position]
        }
    }

    inner class SearchResViewHolder(viewBinding: ItemSearchResBinding) :
            BaseSearchViewHolder(viewBinding.root) {

        private val mViewBinding: ItemSearchResBinding = viewBinding

        init {
            mViewBinding.root.setOnClickListener {
                mResListener.onRestaurantClicked(getRestaurant())
            }
        }

        override fun bind(position: Int) {

            val restaurant = getRestaurant()
            mViewBinding.resInfo = restaurant

            RestaurantInfoUtil.loadFeatureImage(mViewBinding.ivCuisine, restaurant, false)
        }

        private fun getRestaurant() : RestaurantInfo {
            val index = adapterPosition - mCuisineList.size - 1
            return mSearchModel.restaurants[index].restaurant
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