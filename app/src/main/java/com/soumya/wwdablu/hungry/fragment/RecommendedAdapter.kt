package com.soumya.wwdablu.hungry.fragment

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ItemRecommendedBinding
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import com.soumya.wwdablu.hungry.model.network.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo

internal class RecommendedAdapter(searchModel: SearchModel) :
        RecyclerView.Adapter<RecommendedAdapter.RecommendedViewHolder>() {

    private val mSearchModel: SearchModel = searchModel

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

            val imageUrl: String = if (restaurant.featuredImage.isNotEmpty() && restaurant.featuredImage.isNotBlank()) {
                restaurant.featuredImage
            } else if (restaurant.thumb.isNotEmpty() && restaurant.thumb.isNotBlank()) {
                restaurant.thumb
            } else {
                ""
            }

            Glide.with(mViewBinding.ivRestaurantImage.context)
                .load(imageUrl)
                .placeholder(R.drawable.default_card_bg)
                .into(mViewBinding.ivRestaurantImage)
        }
    }
}