package com.soumya.wwdablu.hungry.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.network.model.search.RestaurantInfo
import kotlinx.coroutines.*
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*

object RestaurantInfoUtil {

    /**
     * Tries to set the image for the restaurant. It has few fallback methods. It tries for the
     * provided feature image, then thumbnail. If both are absent then tries to get the
     * photos. If that too fails, then default image is displayed. The entire process is both
     * time and bandwidth consuming.
     *
     * @param userPhotoIfFeatureAbsent Set to false to prevent fetching user submitted photos
     */
    fun loadFeatureImage(imageView: AppCompatImageView, restaurant: RestaurantInfo,
                         userPhotoIfFeatureAbsent: Boolean = true) {

        val imageUrl: String = if (restaurant.featuredImage.isNotEmpty() && restaurant.featuredImage.isNotBlank()) {
            restaurant.featuredImage
        } else {
            restaurant.thumb
        }

        if(imageUrl.isNotBlank() && imageUrl.isNotEmpty()) {
            loadImageIntoView(imageUrl, imageView)
        }

        else if(!userPhotoIfFeatureAbsent) {
            imageView.setImageResource(R.drawable.default_card_bg)
            return
        }

        else {

            val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
                Timber.e(throwable)
                imageView.setImageResource(R.drawable.default_card_bg)
            }

            CoroutineScope(Dispatchers.IO).launch(exceptionHandler) {
                val photos = getPhotos(restaurant)
                withContext(Dispatchers.Main) {
                    if(photos.size > 1) {
                        loadImageIntoView(photos[0], imageView)
                    } else {
                        imageView.setImageResource(R.drawable.default_card_bg)
                    }
                }
            }
        }
    }

    private fun loadImageIntoView(imageUrl: String, imageView: ImageView) {

        Glide.with(imageView.context)
            .load(imageUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .placeholder(R.drawable.default_card_bg)
            .into(imageView)
    }

    suspend fun getPhotos(restaurant: RestaurantInfo) : List<String> {

        return withContext(Dispatchers.IO) {
            val photoList: LinkedList<String> = LinkedList()
            val photoUrl: String = restaurant.photosUrl

            if (photoUrl.isNotEmpty() || photoUrl.isNotBlank()) {
                extractPhotoUrls(photoUrl, photoList)
            }

            photoList
        }
    }

    private fun extractPhotoUrls(photoUrl: String, possibleImageUrl: LinkedList<String>) {

        val url = URL(photoUrl)
        val streamReader = BufferedReader(InputStreamReader(url.openStream()))

        var readLine: String? = streamReader.readLine()
        while(readLine != null) {

            val colonSplit = readLine.split(":\\\"")
            if(colonSplit.isNotEmpty()) {
                colonSplit.forEach { me ->
                    if (me.contains("https") && me.contains(".jpg\\\"")) {
                        val iUrl = me.substring(0, me.indexOf(".jpg\\\"") + 4)
                        possibleImageUrl.add(iUrl)
                    }
                }
            }

            readLine = streamReader.readLine()
        }

        streamReader.close()
    }
}