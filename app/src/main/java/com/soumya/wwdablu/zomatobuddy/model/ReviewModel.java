package com.soumya.wwdablu.zomatobuddy.model;

import com.soumya.wwdablu.zomatobuddy.dagger.DaggerNetworkComponent;
import com.soumya.wwdablu.zomatobuddy.dagger.NetworkModule;
import com.soumya.wwdablu.zomatobuddy.model.reviews.ReviewResponse;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class ReviewModel {

    @Inject
    ZomatoServiceApi zomatoServiceApi;

    private ReviewResponse reviewResponse;
    private DisposableObserver disposableObserver;

    public ReviewModel(String baseUrl) {

        //Instead of accessing BuildConfig, in here we are taking it as
        //an input from the constructor.
        DaggerNetworkComponent.builder()
            .networkModule(new NetworkModule(baseUrl))
            .build()
            .inject(this);
    }

    public Observable<ReviewResponse> getReviews(String restaurantId) {

        return Observable.create(emitter -> {

            if(null != reviewResponse) {
                emitter.onNext(reviewResponse);
                emitter.onComplete();
                return;
            }

            disposableObserver = zomatoServiceApi.getReviews(restaurantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ReviewResponse>() {
                    @Override
                    public void onNext(ReviewResponse reviewResponse) {
                        ReviewModel.this.reviewResponse = reviewResponse;
                        emitter.onNext(reviewResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e("Error while getting review list. %s", e.getMessage());
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                    }
                });
        });
    }
}
