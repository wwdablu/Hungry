package com.soumya.wwdablu.hungry.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soumya.wwdablu.hungry.databinding.CardResInfoBinding
import com.soumya.wwdablu.hungry.iface.RestaurantItemSelector
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import com.soumya.wwdablu.hungry.network.model.search.SearchModel
import com.soumya.wwdablu.hungry.repository.HungryRepo
import com.soumya.wwdablu.hungry.utils.RestaurantInfoUtil
import timber.log.Timber

internal class GenericSearchResultAdapter(searchModel: SearchModel, listener: RestaurantItemSelector) :
        RecyclerView.Adapter<GenericSearchResultAdapter.GenericSearchViewHolder>() {

    private val mSearchModel: SearchModel = searchModel
    private val mListener: RestaurantItemSelector = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericSearchViewHolder {

        return GenericSearchViewHolder(CardResInfoBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: GenericSearchViewHolder, position: Int) {
        holder.bind(mSearchModel.restaurants[position].restaurant)
    }

    override fun getItemCount(): Int {
        return mSearchModel.restaurants.size
    }

    fun handleTouchEvent(motionEvent: MotionEvent, viewHolder: RecyclerView.ViewHolder) {

        val holder: GenericSearchViewHolder = viewHolder as GenericSearchViewHolder

        when (motionEvent.actionMasked) {

            MotionEvent.ACTION_DOWN -> {
                holder.onTouchDown()
            }

            MotionEvent.ACTION_MOVE -> {
                holder.onTouchMove()
            }

            MotionEvent.ACTION_UP -> {
                holder.onTouchUp()
            }
        }
    }

    inner class GenericSearchViewHolder(viewBinding: CardResInfoBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        private val mViewBinding: CardResInfoBinding = viewBinding

        init {

            mViewBinding.ivBookmark.setOnClickListener {
                //
            }

            mViewBinding.root.setOnClickListener {
                mListener.onRestaurantClicked(mSearchModel.restaurants[adapterPosition].restaurant)
            }
        }

        fun onTouchDown() {
            mViewBinding.cvRestaurant.shrinkCard()
        }

        fun onTouchMove() {
            //
        }

        fun onTouchUp() {
            mViewBinding.cvRestaurant.expandCard()
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