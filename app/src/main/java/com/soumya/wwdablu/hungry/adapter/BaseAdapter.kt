package com.soumya.wwdablu.hungry.adapter

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

abstract class BaseAdapter<T: RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    protected fun loadImageByUrl(imageView: ImageView, url: String, @DrawableRes defResId: Int) {
        Glide.with(imageView)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(defResId)
            .into(imageView)
    }
}