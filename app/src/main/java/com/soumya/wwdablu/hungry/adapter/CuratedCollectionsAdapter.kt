package com.soumya.wwdablu.hungry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ItemCuratedCollectionBinding
import com.soumya.wwdablu.hungry.fragment.iface.CollectionItemSelector
import com.soumya.wwdablu.hungry.model.network.collections.CollectionInfo
import com.soumya.wwdablu.hungry.model.network.collections.CuratedCollection

class CuratedCollectionsAdapter(list: List<CuratedCollection>, listener: CollectionItemSelector) :
        RecyclerView.Adapter<CuratedCollectionsAdapter.CollectionViewHolder>() {

    private val MAX_COLLECTION_CARDS: Int = 6

    private val mCollectionList: List<CuratedCollection> = list
    private val mListener: CollectionItemSelector = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {

        val viewBinding: ItemCuratedCollectionBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_curated_collection, parent, false)

        return CollectionViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(mCollectionList[position].collection)
    }

    override fun getItemCount(): Int {
        return if(mCollectionList.size >= MAX_COLLECTION_CARDS) {
            MAX_COLLECTION_CARDS
        } else {
            mCollectionList.size
        }
    }

    inner class CollectionViewHolder(viewBinding: ItemCuratedCollectionBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        private val mViewBinding: ItemCuratedCollectionBinding = viewBinding

        init {
            mViewBinding.root.setOnClickListener {
                mListener.onCollectionClicked(mCollectionList[adapterPosition].collection)
            }
        }

        fun bind(collectionInfo: CollectionInfo) {

            mViewBinding.collection = collectionInfo

            Glide.with(mViewBinding.ivCollectionImage.context)
                    .load(collectionInfo.imageUrl)
                    .placeholder(R.drawable.default_card_bg)
                    .into(mViewBinding.ivCollectionImage)
        }
    }
}