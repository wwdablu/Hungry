package com.soumya.wwdablu.hungry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ItemReviewInfoBinding
import com.soumya.wwdablu.hungry.network.model.reviews.Review
import com.soumya.wwdablu.hungry.network.model.reviews.ReviewModel

class ReviewAdapter(reviewModel: ReviewModel) : BaseAdapter<ReviewAdapter.ReviewViewHolder>() {

    private val mReviewModel: ReviewModel = reviewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {

        return ReviewViewHolder(ItemReviewInfoBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        holder.bind(mReviewModel.reviewList[position].review)
    }

    override fun getItemCount(): Int {
        return mReviewModel.reviewList.size
    }

    inner class ReviewViewHolder(viewBinding: ItemReviewInfoBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        private val mViewBinding: ItemReviewInfoBinding = viewBinding

        fun bind(review: Review) {
            mViewBinding.review = review
            loadImageByUrl(mViewBinding.ivUserImage, review.user.profileImage, R.drawable.default_card_bg)
        }
    }
}