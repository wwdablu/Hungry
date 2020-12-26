package com.soumya.wwdablu.hungry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soumya.wwdablu.hungry.databinding.ItemRecommendedBinding
import com.soumya.wwdablu.hungry.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil

internal class GenericSearchResultAdapter(searchModel: SearchModel, listener: RestaurantItemSelector) :
        RecyclerView.Adapter<GenericSearchResultAdapter.RecommendedViewHolder>() {

    private val mSearchModel: SearchModel = searchModel
    private val mListener: RestaurantItemSelector = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendedViewHolder {

        return RecommendedViewHolder(ItemRecommendedBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: RecommendedViewHolder, position: Int) {
        holder.bind(mSearchModel.restaurants[position].restaurant)
    }

    override fun getItemCount(): Int {
        return mSearchModel.restaurants.size
    }

    inner class RecommendedViewHolder(viewBinding: ItemRecommendedBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        private val mViewBinding: ItemRecommendedBinding = viewBinding

        init {
            mViewBinding.root.setOnClickListener {
                mListener.onRestaurantClicked(mSearchModel.restaurants[adapterPosition].restaurant)
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(restaurant: RestaurantInfo) {

            mViewBinding.resName.text = restaurant.name
            mViewBinding.resCuisines.text = restaurant.cuisines
            mViewBinding.resLocation.text = "${restaurant.location.locality}, ${restaurant.location.city}"
            mViewBinding.resAvgCost.text = "${restaurant.averageCostForTwo}"
            mViewBinding.resTiming.text = restaurant.timings.split(",")[0]

            mViewBinding.tvDistance.text = RestaurantInfo.calculateDistance(
                    restaurant.location.latitude, restaurant.location.longitude,
                    HungryRepo.getLocation().first, HungryRepo.getLocation().second
            )

            RestaurantInfoUtil.loadFeatureImage(mViewBinding.ivRestaurantImage, restaurant)
        }
    }
}