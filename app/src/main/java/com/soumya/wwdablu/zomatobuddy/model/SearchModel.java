package com.soumya.wwdablu.zomatobuddy.model;

import com.google.gson.Gson;
import com.soumya.wwdablu.zomatobuddy.BuildConfig;
import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.dagger.DaggerNetworkComponent;
import com.soumya.wwdablu.zomatobuddy.dagger.NetworkModule;
import com.soumya.wwdablu.zomatobuddy.database.CacheDB;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SearchModel {
    
    @Inject
    ZomatoServiceApi zomatoServiceApi;

    private @SearchTypes.SearchType String searchType;
    private DisposableObserver disposableObserver;

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

            disposableObserver = Observable.defer(() -> Observable.create((ObservableOnSubscribe<String>) cacheEmitter -> {

                String data = CacheDB.getInstance().getFromCache(searchType);
                cacheEmitter.onNext(data);

            })).flatMap(responseCache -> {

                if(!"".equals(responseCache.trim())) {
                    return Observable.just(new Gson().fromJson(responseCache, SearchResponse.class));
                }

                return zomatoServiceApi.getCategorySearch(Double.toString(locationCoordinates.getLatitude()),
                        Double.toString(locationCoordinates.getLongitude()), searchType);

            }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {

                        cacheSearchResponse(searchResponse);
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

    private void cacheSearchResponse(SearchResponse searchResponse) {

        Observable.just(searchResponse)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribeWith(new DisposableObserver<SearchResponse>() {
                @Override
                public void onNext(SearchResponse response) {
                    CacheDB.getInstance().cache(searchType, new Gson().toJson(searchResponse));
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e("Could not store the information in the cache for %s", searchType);
                }

                @Override
                public void onComplete() {

                }
            });
    }
}
