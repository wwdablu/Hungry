package com.soumya.wwdablu.zomatobuddy.model;

import com.soumya.wwdablu.zomatobuddy.common.Analytics;
import com.soumya.wwdablu.zomatobuddy.dagger.DaggerNetworkComponent;
import com.soumya.wwdablu.zomatobuddy.dagger.NetworkModule;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchModel {

    @Inject
    ZomatoServiceApi zomatoServiceApi;

    private DisposableObserver disposableObserver;
    private LocationCoordinates locationCoordinates;

    public SearchModel(String baseUrl, LocationCoordinates locationCoordinates) {

        this.locationCoordinates = locationCoordinates;

        DaggerNetworkComponent.builder()
            .networkModule(new NetworkModule(baseUrl))
            .build()
            .inject(this);
    }

    public Observable<SearchResponse> search(String searchText) {

        if(null != disposableObserver && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }

        return Observable.create(emitter -> disposableObserver = zomatoServiceApi.getsearchResults(locationCoordinates.getLatitude(),
                locationCoordinates.getLongitude(), searchText, 20, 5000)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<SearchResponse>() {

                @Override
                public void onNext(SearchResponse searchResponse) {
                    emitter.onNext(searchResponse);

                    //Analytics information
                    Analytics.setUserAction(Analytics.EVENT_SEARCH, Analytics.PARAM_SEARCH_TERM,
                            searchText);
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e("Error while searching for user query. %s", e.getMessage());
                    emitter.onError(e);
                }

                @Override
                public void onComplete() {
                    emitter.onComplete();
                }
            }));
    }
}
