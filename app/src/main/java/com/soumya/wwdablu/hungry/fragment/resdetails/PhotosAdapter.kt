package com.soumya.wwdablu.hungry.fragment.resdetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.databinding.ItemResPhotoBinding

class PhotosAdapter(urlList: List<String>) : RecyclerView.Adapter<PhotosAdapter.PhotosViewModel>() {

    private val mUrlList: List<String> = urlList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosViewModel {

        val viewBinding: ItemResPhotoBinding = ItemResPhotoBinding.inflate(LayoutInflater.from(parent.context),
                parent, false)

        return PhotosViewModel(viewBinding)
    }

    override fun onBindViewHolder(holder: PhotosViewModel, position: Int) {
        holder.bind(mUrlList[position])
    }

    override fun getItemCount(): Int {
        return mUrlList.size
    }

    inner class PhotosViewModel(viewBinding: ItemResPhotoBinding) : RecyclerView.ViewHolder(viewBinding.root) {

        private val mViewBinding: ItemResPhotoBinding = viewBinding

        fun bind(url: String) {

            Glide.with(mViewBinding.ivResPhoto.context)
                    .load(url)
                    .placeholder(R.drawable.default_card_bg)
                    .into(mViewBinding.ivResPhoto)
        }
    }
}