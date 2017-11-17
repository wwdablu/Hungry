package com.soumya.wwdablu.zomatobuddy.viewmodel;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;

import com.soumya.wwdablu.zomatobuddy.common.SearchTypes;
import com.soumya.wwdablu.zomatobuddy.model.LocationCoordinates;
import com.soumya.wwdablu.zomatobuddy.model.SearchModel;
import com.soumya.wwdablu.zomatobuddy.model.search.SearchResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class CategoryViewModel extends BaseObservable {

    public interface ISearchResult {
        void onSearchComplete(SearchResponse searchResponse);
    }

    private SearchModel searchModel;
    private ObservableField<String> headerTitle;
    private ObservableField<String> headerSubTitle;

    public CategoryViewModel(@SearchTypes.SearchType String searchType, LocationCoordinates locationCoordinates,
                             final ISearchResult searchResult) {

        headerTitle = new ObservableField<>();
        headerSubTitle = new ObservableField<>();

        //Set the type of the search
        searchModel = new SearchModel(searchType);

        //Search based on the location and search type
        searchModel.search(locationCoordinates)
            .observeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<SearchResponse>() {
                @Override
                public void onNext(SearchResponse searchResponse) {
                    searchResult.onSearchComplete(searchResponse);
                    Timber.d("Search result is: %s", searchResponse.toString());
                }

                @Override
                public void onError(Throwable e) {
                    Timber.e(e);
                }

                @Override
                public void onComplete() {

                }
            });
    }

    @Bindable
    public ObservableField<String> getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle.set(headerTitle);
    }

    @Bindable
    public ObservableField<String> getHeaderSubTitle() {
        return headerSubTitle;
    }

    public void setHeaderSubTitle(String headerSubTitle) {
        this.headerSubTitle.set(headerSubTitle);
    }
}
