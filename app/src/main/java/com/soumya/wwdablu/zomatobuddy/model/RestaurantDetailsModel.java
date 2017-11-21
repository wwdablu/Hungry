package com.soumya.wwdablu.zomatobuddy.model;

import com.soumya.wwdablu.zomatobuddy.BuildConfig;
import com.soumya.wwdablu.zomatobuddy.dagger.DaggerNetworkComponent;
import com.soumya.wwdablu.zomatobuddy.dagger.NetworkModule;
import com.soumya.wwdablu.zomatobuddy.model.restaurant.RestaurantResponse;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class RestaurantDetailsModel {

    @Inject
    ZomatoServiceApi zomatoServiceApi;

    private DisposableObserver disposableObserver;
    private RestaurantResponse restaurantResponse;

    public RestaurantDetailsModel() {

        DaggerNetworkComponent.builder()
            .networkModule(new NetworkModule(BuildConfig.ZOMATO_BASE_URL))
            .build()
            .inject(this);
    }

    public Observable<RestaurantResponse> getRestaurantDetails(String resId) {

        return Observable.create(emitter -> {

            if (null != restaurantResponse) {
                emitter.onNext(restaurantResponse);
                emitter.onComplete();
                return;
            }

            disposableObserver = zomatoServiceApi.getRestaurantDetails(resId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<RestaurantResponse>() {
                    @Override
                    public void onNext(RestaurantResponse restaurantResponse) {

                        RestaurantDetailsModel.this.restaurantResponse = restaurantResponse;
                        emitter.onNext(restaurantResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error while fetching restaurant details. %s", e.getMessage());
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }
                });
        });
    }

    public String getMenuUrl() {
        return restaurantResponse.getMenuUrl();
    }

    public String getPhotoUrl() {
        return restaurantResponse.getPhotosUrl();
    }

    public String getWebsite() {
        return restaurantResponse.getUrl();
    }

    public String getZomatoLink() {
        return restaurantResponse.getDeeplink();
    }

    public void clean() {

        if (null != disposableObserver && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }
}