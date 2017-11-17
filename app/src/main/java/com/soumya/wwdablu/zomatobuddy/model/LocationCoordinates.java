package com.soumya.wwdablu.zomatobuddy.model;

import org.parceler.Parcel;

@Parcel
public class LocationCoordinates {

    public double longitude;
    public double latitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
