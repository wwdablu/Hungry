package com.soumya.wwdablu.zomatobuddy.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.soumya.wwdablu.zomatobuddy.BuildConfig;
import com.soumya.wwdablu.zomatobuddy.R;
import com.soumya.wwdablu.zomatobuddy.dagger.DaggerNetworkComponent;
import com.soumya.wwdablu.zomatobuddy.dagger.NetworkModule;
import com.soumya.wwdablu.zomatobuddy.model.LocationCoordinates;
import com.soumya.wwdablu.zomatobuddy.model.categories.CategoryResponse;
import com.soumya.wwdablu.zomatobuddy.network.ZomatoServiceApi;

import org.parceler.Parcels;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class SplashActivity extends AppCompatActivity {

    private static final int LOCATION_REQUEST_CODE = 1001;

    @Inject
    ZomatoServiceApi zomatoServiceApi;

    private DisposableObserver disposableObserver;
    private CategoryResponse categoryResponse;
    private Location location;
    private FusedLocationProviderClient locationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        DaggerNetworkComponent.builder()
            .networkModule(new NetworkModule(BuildConfig.ZOMATO_BASE_URL))
            .build()
            .inject(this);

        confirmUserAction();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (null != disposableObserver && !disposableObserver.isDisposed()) {
            disposableObserver.dispose();
        }

        locationProviderClient.removeLocationUpdates(locationCallback);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {

            case LOCATION_REQUEST_CODE:
                if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                    fetchLocationInformation();
                } else {
                    location = null;
                    fetchDataFromZomatoToProceed();
                }
                break;

            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void requestLocationAccess() {

        //If the permission has been provided
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {

            fetchLocationInformation();
            return;
        }

        //Request the permission from the user
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                LOCATION_REQUEST_CODE);
    }

    private void fetchLocationInformation() {

        //Guard check
        if (ActivityCompat.checkSelfPermission(SplashActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        locationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());

        //Listen for the last location from the provider
        locationProviderClient.getLastLocation()
            .addOnSuccessListener(loc -> {

                //We did not get the last location, lets get it
                if(null == loc) {

                    LocationRequest locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(10000)
                        .setFastestInterval(1000);

                    locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());

                } else {
                    location = loc;
                    fetchDataFromZomatoToProceed();
                }
            })
            .addOnFailureListener(e -> {
                location = null;
                fetchDataFromZomatoToProceed();
            });
    }

    private LocationCallback locationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            super.onLocationResult(locationResult);
            location = locationResult.getLastLocation();
            locationProviderClient.removeLocationUpdates(locationCallback);
            fetchDataFromZomatoToProceed();
        }
    };

    private void fetchDataFromZomatoToProceed() {

        disposableObserver = zomatoServiceApi.getCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(new DisposableObserver<CategoryResponse>() {
                @Override
                public void onNext(CategoryResponse catRes) {
                    categoryResponse = catRes;
                    Timber.d("The category has been received: %s", categoryResponse.getCategories().size());
                }

                @Override
                public void onError(Throwable e) {
                    finish();
                }

                @Override
                public void onComplete() {
                    launchNextScreen();
                }
            });
    }

    private void launchNextScreen() {

        LocationCoordinates locationCoordinates = new LocationCoordinates();
        locationCoordinates.setLongitude(null == location ? Double.parseDouble(BuildConfig.DEFAULT_LOGITUDE)
                : location.getLongitude());

        locationCoordinates.setLatitude(null == location ? Double.parseDouble(BuildConfig.DEFAULT_LATITUDE)
                : location.getLatitude());

        Bundle bundle = new Bundle();
        bundle.putParcelable("categoryResponse", Parcels.wrap(categoryResponse));
        bundle.putParcelable("location", Parcels.wrap(locationCoordinates));

        Intent dashboardIntent = new Intent(this, DashboardActivity.class);
        dashboardIntent.putExtra("data", bundle);

        startActivity(dashboardIntent);
        finish();
    }

    private void confirmUserAction() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
            .setTitle("ATTENTION")
            .setMessage(R.string.notify_user)
            .setPositiveButton("I Agree", (dialogInterface, i) -> requestLocationAccess())
            .setNegativeButton("Exit", (dialogInterface, i) -> finish())
            .setCancelable(false);
        builder.create().show();
    }
}
