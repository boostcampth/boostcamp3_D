package com.teamdonut.eatto.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import com.google.android.gms.location.*;
import com.teamdonut.eatto.ui.map.MapNavigator;

import java.lang.ref.WeakReference;

public class GpsModule {
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 15000, FASTEST_INTERVAL = 10000;
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationCallback mLocationCallback;
    private final Context mContext;
    public GpsModule(WeakReference<Context> context, MapNavigator mapNavigator){
        mContext = context.get();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        mLocationCallback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    if(location != null){
                        ActivityUtils.saveStrValueSharedPreferences(mContext, "gps", "longitude", String.valueOf(location.getLongitude()));
                        ActivityUtils.saveStrValueSharedPreferences(mContext, "gps", "latitude", String.valueOf(location.getLatitude()));
                        stopLocationUpdates();
                        mapNavigator.setMyPosition();
                    }
                }
            }
        };
        locationRequest = new LocationRequest()
                .setFastestInterval(FASTEST_INTERVAL)
                .setInterval(UPDATE_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    public void stopLocationUpdates(){
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }
}
