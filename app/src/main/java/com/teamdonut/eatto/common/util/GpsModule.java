package com.teamdonut.eatto.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Location;
import android.os.Looper;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.teamdonut.eatto.ui.main.MainActivity;
import com.teamdonut.eatto.ui.map.MapNavigator;

import java.lang.ref.WeakReference;

public class GpsModule {
    private LocationRequest locationRequest;
    private static final long UPDATE_INTERVAL = 15000, FASTEST_INTERVAL = 10000;
    private FusedLocationProviderClient mFusedLocationClient;
    private MapNavigator mapNavigator;
    private final Context mContext;
    private LocationCallback mLocationCallback = new LocationCallback(){
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
                    if(mContext instanceof MainActivity) {
                        mapNavigator.setMyPosition();
                    }
                }
            }
        }
    };

    public GpsModule(WeakReference<Context> context, MapNavigator mapNavigator){
        mContext = context.get();
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        this.mapNavigator = mapNavigator;
        locationRequest = new LocationRequest()
                .setFastestInterval(FASTEST_INTERVAL)
                .setInterval(UPDATE_INTERVAL)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void stopLocationUpdates(){
        if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }
    }

    @SuppressLint("MissingPermission")
    public void startLocationUpdates() {
        mFusedLocationClient.requestLocationUpdates(locationRequest, mLocationCallback, Looper.myLooper());
    }
}
