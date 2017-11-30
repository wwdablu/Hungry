package com.soumya.wwdablu.zomatobuddy.model;

import com.soumya.wwdablu.zomatobuddy.model.favourite.FavouriteData;

import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class Favourites {

    public enum FavAction {
        ADD,
        REMOVE,
        LIST
    }

    public interface ITransactionStatus {
        void onSuccess(FavAction action);
        void onError(FavAction action);
        void onFavListRetrieved(LinkedList<FavouriteData> favouriteDataList);
    }

    public static void addFavourite(String resId, String resName, String resLocation, String resCuisines,
                                    ITransactionStatus callbackStatus) {


        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {

            try (Realm db = Realm.getDefaultInstance()) {

                db.executeTransaction(realm -> {

                    FavouriteData data = realm.where(FavouriteData.class).equalTo("resId", resId).findFirst();

                    //The data already exists
                    if(null != data) {

                        data.setResId(resId);
                        data.setResName(resName);
                        data.setResCuisines(resCuisines);
                        data.setResLocation(resLocation);

                    } else {

                        Number id = realm.where(FavouriteData.class).max("id");
                        FavouriteData newData = realm.createObject(FavouriteData.class, null == id ? 1 : id.intValue() + 1);

                        newData.setResId(resId);
                        newData.setResName(resName);
                        newData.setResCuisines(resCuisines);
                        newData.setResLocation(resLocation);
                    }

                    emitter.onNext(true);
                });
            }
        })

        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())

        .subscribeWith(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean boolVal) {
                if(null != callbackStatus) {
                    callbackStatus.onSuccess(FavAction.ADD);
                }
            }

            @Override
            public void onError(Throwable e) {

                Timber.e("Could not add to favourite. %s", e.getMessage());

                if(null != callbackStatus) {
                    callbackStatus.onError(FavAction.ADD);
                }
            }

            @Override
            public void onComplete() {
                //
            }
        });
    }

    public static void removeFavourite(String resId, ITransactionStatus callbackStatus) {

        Observable.create((ObservableOnSubscribe<Boolean>) emitter -> {

            try (Realm db = Realm.getDefaultInstance()) {

                db.executeTransaction(realm -> {

                    FavouriteData result = realm.where(FavouriteData.class).equalTo("resId", resId).findFirst();

                    if(null != result) {
                        result.deleteFromRealm();
                        emitter.onNext(true);
                        return;
                    }

                    emitter.onNext(false);
                });
            }
        })

        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

        .subscribeWith(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean aBoolean) {

                Timber.d("Removed from favourite: %s", aBoolean);
                if(null != callbackStatus) {
                    callbackStatus.onSuccess(FavAction.REMOVE);
                }
            }

            @Override
            public void onError(Throwable e) {
                Timber.e("Error while removing from fav list. %s", e.getMessage());
                if(null != callbackStatus) {
                    callbackStatus.onError(FavAction.REMOVE);
                }
            }

            @Override
            public void onComplete() {
                //
            }
        });
    }

    public static void getFavouriteDataList(ITransactionStatus callbackStatus) {

        Observable.create((ObservableOnSubscribe<LinkedList<FavouriteData>>) emitter -> {

            try (Realm db = Realm.getDefaultInstance()) {

                db.executeTransaction(realm -> {

                    RealmResults<FavouriteData> result = realm.where(FavouriteData.class).findAll();
                    LinkedList<FavouriteData> resultData = new LinkedList<>();

                    for(FavouriteData data : result) {
                        resultData.add(new FavouriteData(data));
                    }

                    emitter.onNext(resultData);
                });
            }
        })

        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())

        .subscribeWith(new DisposableObserver<LinkedList<FavouriteData>>() {
            @Override
            public void onNext(LinkedList<FavouriteData> dataList) {

                if(null != callbackStatus) {
                    callbackStatus.onFavListRetrieved(dataList);
                }
            }

            @Override
            public void onError(Throwable e) {

                if(null != callbackStatus) {
                    callbackStatus.onError(FavAction.LIST);
                }
            }

            @Override
            public void onComplete() {
                //
            }
        });
    }

    public static Observable<Boolean> isFavouriteRestaurant(final String resId) {

        return Observable.create(emitter -> {

            try (Realm db = Realm.getDefaultInstance()) {

                db.executeTransaction(realm -> {

                    FavouriteData result = realm.where(FavouriteData.class).equalTo("resId", resId).findFirst();

                    emitter.onNext(null != result);
                });
            }
        });
    }
}
