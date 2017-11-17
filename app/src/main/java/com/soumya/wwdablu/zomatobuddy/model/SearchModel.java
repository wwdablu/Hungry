package com.soumya.wwdablu.zomatobuddy.model;

import com.soumya.wwdablu.zomatobuddy.BuildConfig;
import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.dagger.DaggerNetworkComponent;
import com.soumya.wwdablu.zomatobuddy.dagger.NetworkModule;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class SearchModel {
    
    @Inject
    ZomatoServiceApi zomatoServiceApi;

    private @SearchTypes.SearchType String searchType;
    private DisposableObserver disposableObserver;
    private SearchResponse searchResponse;

    public SearchModel(@SearchTypes.SearchType String searchType) {

        this.searchType = searchType;

        DaggerNetworkComponent.builder()
            .networkModule(new NetworkModule(BuildConfig.ZOMATO_BASE_URL))
            .build()
            .inject(this);
    }

    /**
     * Performs a search on the location
     * @param locationCoordinates Provides the coordinate of the location
     * @return Observable which will be notified when the data is available
     */
    public Observable<SearchResponse> search(LocationCoordinates locationCoordinates) {

        //This returns the observable which will fire once an observer is subscribed with it
        return Observable.create(emitter -> {

            //If the data is already present, then just use it
            if(null != searchResponse) {
                emitter.onNext(searchResponse);
                emitter.onComplete();
                return;
            }

            disposableObserver = zomatoServiceApi.getCategorySearch(Double.toString(locationCoordinates.getLatitude()),
                Double.toString(locationCoordinates.getLongitude()), searchType)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribeWith(new DisposableObserver<SearchResponse>() {

                    @Override
                    public void onNext(SearchResponse searchResponse) {

                        SearchModel.this.searchResponse = searchResponse;
                        emitter.onNext(searchResponse);
                    }

                    @Override
                    public void onError(Throwable e) {
                        emitter.onError(e);
                    }

                    @Override
                    public void onComplete() {
                        emitter.onComplete();
                        clean();
                    }
                });
        });
    }

    /**
     * Must call this method when the DataModel is no longer required.
     */
    public void clean() {

        if(null != disposableObserver && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }
    }
}
