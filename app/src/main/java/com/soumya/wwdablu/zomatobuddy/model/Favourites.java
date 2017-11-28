package com.soumya.wwdablu.zomatobuddy.model;

import com.soumya.wwdablu.zomatobuddy.model.favourite.FavouriteData;

import java.util.LinkedList;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

public class Favourites {

    public interface ITransactionStatus {
        void onSuccess();
        void onError();
        void onFavListRetrieved(LinkedList<FavouriteData> favouriteDataList);
    }

    public static void addFavourite(String resId, String resName, String resLocation, String resCuisines,
                                    ITransactionStatus callbackStatus) {


        Observable.create(new ObservableOnSubscribe<Boolean>() {

            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {

                final Realm database = Realm.getDefaultInstance();

                database.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

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
                    }
                });
            }
        })

        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())

        .subscribeWith(new DisposableObserver<Boolean>() {
            @Override
            public void onNext(Boolean boolVal) {
                if(null != callbackStatus) {
                    callbackStatus.onSuccess();
                }
            }

            @Override
            public void onError(Throwable e) {

                Timber.e("Could not add to favourite. %s", e.getMessage());

                if(null != callbackStatus) {
                    callbackStatus.onError();
                }
            }

            @Override
            public void onComplete() {
                //
            }
        });
    }

    public static void getFavouriteDataList(ITransactionStatus callbackStatus) {

        Observable.create(new ObservableOnSubscribe<LinkedList<FavouriteData>>() {
            @Override
            public void subscribe(ObservableEmitter<LinkedList<FavouriteData>> emitter) throws Exception {

                final Realm database = Realm.getDefaultInstance();

                database.executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {

                        RealmResults<FavouriteData> result = realm.where(FavouriteData.class).findAll();
                        LinkedList<FavouriteData> resultData = new LinkedList<>();

                        for(FavouriteData data : result) {
                            resultData.add(new FavouriteData(data));
                        }

                        //database.close();
                        emitter.onNext(resultData);
                    }
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
                    callbackStatus.onError();
                }
            }

            @Override
            public void onComplete() {
                //
            }
        });
    }
}
