package com.soumya.wwdablu.hungry.utils

import com.soumya.wwdablu.hungry.model.network.search.RestaurantInfo
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.schedulers.Schedulers
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.*

object RestaurantInfoUtil {

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
                        Timber.d(iUrl)
                        possibleImageUrl.add(iUrl)
                    }
                }
            }

            readLine = streamReader.readLine()
        }

        streamReader.close()
    }
}