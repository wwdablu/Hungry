package com.soumya.wwdablu.zomatobuddy.model.favourite;

import io.realm.RealmObject;
import io.realm.annotations.Index;
import io.realm.annotations.PrimaryKey;

public class FavouriteData extends RealmObject {

    @PrimaryKey
    long id;

    @Index
    private String resId;

    private String resName;
    private String resLocation;
    private String resCuisines;

    public FavouriteData() {
        super();
    }

    public FavouriteData(FavouriteData model) {

        this.id = model.id;
        this.resId = model.resId;
        this.resName = model.resName;
        this.resCuisines = model.resCuisines;
        this.resLocation = model.resLocation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getResId() {
        return resId;
    }

    public void setResId(String resId) {
        this.resId = resId;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public String getResLocation() {
        return resLocation;
    }

    public void setResLocation(String resLocation) {
        this.resLocation = resLocation;
    }

    public String getResCuisines() {
        return resCuisines;
    }

    public void setResCuisines(String resCuisines) {
        this.resCuisines = resCuisines;
    }
}
