package com.soumya.wwdablu.hungry.utils

import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.soumya.wwdablu.hungry.R
import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
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
            getPhotos(restaurant)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object: DisposableObserver<List<String>>() {
                    override fun onNext(t: List<String>?) {

                        if(t == null || t.isEmpty()) {
                            imageView.setImageResource(R.drawable.default_card_bg)
                            return
                        }

                        loadImageIntoView(t[0], imageView)
                    }

                    override fun onError(e: Throwable?) {
                        imageView.setImageResource(R.drawable.default_card_bg)
                    }

                    override fun onComplete() {
                        //
                    }
                })
        }
    }

    private fun loadImageIntoView(imageUrl: String, imageView: ImageView) {

        Glide.with(imageView.context)
            .load(imageUrl)
            .placeholder(R.drawable.default_card_bg)
            .into(imageView)
    }

    fun getPhotos(restaurant: RestaurantInfo) : Observable<List<String>> {

        return Observable.create(ObservableOnSubscribe<List<String>> {

            val photoList: LinkedList<String> = LinkedList()
            val photoUrl: String = restaurant.photosUrl

            if (photoUrl.isEmpty() || photoUrl.isBlank()) {
                it.onNext(photoList)
                it.onComplete()
                return@ObservableOnSubscribe
            }

            extractPhotoUrls(photoUrl, photoList)

            it.onNext(photoList)
            it.onComplete()

        }).subscribeOn(Schedulers.io())
    }

    private fun extractPhotoUrls(photoUrl: String, possibleImageUrl: LinkedList<String>) {

        val url: URL = URL(photoUrl)
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