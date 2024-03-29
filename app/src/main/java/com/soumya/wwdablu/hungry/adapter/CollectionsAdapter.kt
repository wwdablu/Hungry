package com.soumya.wwdablu.hungry.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ItemCollectionLongBinding
import com.soumya.wwdablu.hungry.iface.CollectionItemSelector
import com.soumya.wwdablu.hungry.network.model.collections.CollectionInfo
import com.soumya.wwdablu.hungry.network.model.collections.CuratedCollection

class CollectionsAdapter(list: List<CuratedCollection>, listener: CollectionItemSelector) :
            BaseAdapter<CollectionsAdapter.CollectionViewHolder>() {

    private val mCollectionList: List<CuratedCollection> = list
    private val mListener: CollectionItemSelector = listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {

        val viewBinding: ItemCollectionLongBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_collection_long, parent, false)

        return CollectionViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        holder.bind(mCollectionList[position].collection)
    }

    override fun getItemCount(): Int {
        return mCollectionList.size
    }

    inner class CollectionViewHolder(viewBinding: ItemCollectionLongBinding) :
            RecyclerView.ViewHolder(viewBinding.root) {

        private val mViewBinding: ItemCollectionLongBinding = viewBinding

        init {
            mViewBinding.root.setOnClickListener {
                mListener.onCollectionClicked(mCollectionList[bindingAdapterPosition].collection)
            }
        }

        fun bind(collection: CollectionInfo) {

            mViewBinding.collection = collection
            loadImageByUrl(mViewBinding.ivCollectionImage, collection.imageUrl, R.drawable.default_card_bg)
        }
    }
}