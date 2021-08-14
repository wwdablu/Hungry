package com.soumya.wwdablu.hungry.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.activity.PhotoViewerActivity
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

        init {
            mViewBinding.root.setOnClickListener {
                val intent: Intent = Intent(viewBinding.root.context, PhotoViewerActivity::class.java)
                intent.putStringArrayListExtra("list", ArrayList(mUrlList))
                intent.putExtra("index", bindingAdapterPosition)
                viewBinding.root.context.startActivity(intent)
            }
        }

        fun bind(url: String) {

            Glide.with(mViewBinding.ivResPhoto.context)
                    .load(url)
                    .placeholder(R.drawable.default_card_bg)
                    .into(mViewBinding.ivResPhoto)
        }
    }
}